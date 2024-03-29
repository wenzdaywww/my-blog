package com.www.myblog.blog.service.browse.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.exception.BusinessException;
import com.www.common.data.response.Result;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.entity.UserFansEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.BlogCommentMapper;
import com.www.myblog.blog.data.mapper.BlogGroupMapper;
import com.www.myblog.blog.data.mapper.BlogTagMapper;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.browse.IBlogBrowseService;
import com.www.myblog.blog.service.entity.IBlogArticleService;
import com.www.myblog.blog.service.entity.IBlogCollectService;
import com.www.myblog.blog.service.entity.IBlogPraiseService;
import com.www.myblog.blog.service.redis.IRedisService;
import com.www.myblog.common.config.feign.base.IBaseFeignService;
import com.www.myblog.common.dto.BlogArticleDTO;
import com.www.myblog.common.dto.BlogTagDTO;
import com.www.myblog.common.dto.UserInfoDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>@Description 匿名用户博客浏览Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:19 </p>
 */
@Service
public class BlogBrowseServiceImpl implements IBlogBrowseService {
    @Autowired
    private IBaseFeignService baseFeignService;
    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private BlogGroupMapper userBlogGroupMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private UserFansMapper userFansMapper;
    @Autowired
    private BlogCommentMapper blogCommentMapper;
    @Autowired
    private IBlogCollectService blogCollectService;
    @Autowired
    private IBlogArticleService blogArticleService;
    @Autowired
    private IBlogPraiseService blogPraiseService;
    @Autowired
    private IRedisService redisService;


    /**
     * <p>@Description 获取推荐博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param pageNum 页码
     * @return com.www.common.data.dto.response.Result<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @Override
    public Result<List<BlogArticleDTO>> findTipBlogList(int pageNum) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        long pageSize = 5;
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogArticleMapper.findTipBlogList(page);
        return new Result<>(pageNum,pageSize,page.getTotal(),page.getRecords());
    }

    /**
     * <p>@Description 查询评论列表，包括父评论和子评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 22:34 </p>
     * @param pageNum 页码
     * @param blogId 博客id,不等于null，则是父评论
     * @param parentComId 父评论id，不等于null，则是子评论
     * @return com.www.common.data.dto.response.Result<java.util.List < com.www.myblog.blog.data.dto.CommentDTO>>
     */
    @Override
    public Result<List<CommentDTO>> findCommentList(int pageNum, Long blogId, Long parentComId) {
        Result<List<CommentDTO>> response = new Result<>();
        if((blogId == null && parentComId == null) || (blogId != null && parentComId != null)){
            throw new BusinessException("查询评论列表失败，信息不全");
        }
        long pageSize = 5;//分页查询条数
        pageNum = pageNum <= 0 ? 1 : pageNum;
        Page<CommentDTO> page = new Page<>(pageNum,pageSize);
        page = blogCommentMapper.findCommentList(page,blogId,parentComId);
        List<CommentDTO> commentList =  page.getRecords();
        List<String> userIdList = new ArrayList<>();
        //查询不到信息返回
        if(CollectionUtils.isEmpty(commentList)){
            return new Result<>(pageNum,pageSize,page.getTotal(),null);
        }
        //重新组装评论信息
        for (CommentDTO commentDTO : commentList){
            //将需要查询用户信息的用户id添加到集合中统一查询
            this.addSearchUserId(userIdList,commentDTO);
            if(blogId != null){//父评论
                //查询父评论时默认再查询5条子评论
                Page<CommentDTO> pageChilden = new Page<>(1,pageSize);
                pageChilden = blogCommentMapper.findCommentList(pageChilden,null,commentDTO.getCommentId());
                List<CommentDTO> childrenList =  pageChilden.getRecords();
                if (CollectionUtils.isNotEmpty(childrenList)){
                    for (CommentDTO childrenDTO : childrenList){
                        //将需要查询用户信息的用户id添加到集合中统一查询
                        this.addSearchUserId(userIdList,childrenDTO);
                    }
                }
                //判断子评论是否还有未加载的，计算剩余未加载条数
                commentDTO.setMore(pageChilden.getTotal() >= pageSize ? pageChilden.getTotal() - pageSize : 0L);
                commentDTO.setPageNum(1);//设置子评论页码为1
                commentDTO.setSubList(childrenList);
            }
        }
        //查询用户信息
        if(CollectionUtils.isNotEmpty(userIdList)){
            List<UserInfoDTO> userList = Result.getBackData(baseFeignService.findUserInfoList(userIdList));
            Map<String,UserInfoDTO> userMap = CollectionUtils.isEmpty(userList) ? new HashMap<>() :
                    userList.stream().collect(Collectors.toMap(UserInfoDTO::getUserId, Function.identity(), (key1, key2) -> key2));
            for (CommentDTO parentDTO : commentList){
                //设置父评论人的名称和头像
                UserInfoDTO userInfoDTO = userMap.get(parentDTO.getUserId());
                if(userInfoDTO != null){
                    parentDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
                }
                if(CollectionUtils.isNotEmpty(parentDTO.getSubList())){
                    for (CommentDTO childrenDTO : parentDTO.getSubList()){
                        //设置子评论人的名称和头像
                        UserInfoDTO childrenUserDTO = userMap.get(childrenDTO.getUserId());
                        if(childrenUserDTO != null){
                            childrenDTO.setUserName(childrenUserDTO.getUserName()).setPhoto(childrenUserDTO.getPhoto());
                        }
                        //设置父评论回复人的名称
                        UserInfoDTO replyUserDTO = userMap.get(childrenDTO.getReplyUserId());
                        if(replyUserDTO != null){
                            childrenDTO.setReplyName(replyUserDTO.getUserName());
                        }
                    }
                }
            }
        }
        return new Result<>(pageNum,pageSize,page.getTotal(),commentList);
    }
    /**
     * <p>@Description 将需要查询用户信息的用户id添加到集合中统一查询 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/31 11:37 </p>
     * @param userIdList 用户id集合
     * @param commentDTO 评论信息
     * @return void
     */
    private void addSearchUserId(List<String> userIdList,CommentDTO commentDTO){
        //将需要查询用户信息的用户id添加到集合中统一查询
        if(StringUtils.isNotBlank(commentDTO.getUserId()) && !userIdList.contains(commentDTO.getUserId())){
            userIdList.add(commentDTO.getUserId());
        }
        if(StringUtils.isNotBlank(commentDTO.getReplyUserId()) && !userIdList.contains(commentDTO.getReplyUserId())){
            userIdList.add(commentDTO.getReplyUserId());
        }
        commentDTO.setOpen(false);//默认关闭回复输入框
    }
    /**
     * <p>@Description 根据博客ID查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/25 21:21 </p>
     * @param userId 当前登录用户ID
     * @param ipAddr 访问IP
     * @return com.www.common.data.dto.response.Result<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public Result<BlogArticleDTO> findAriticle(String userId,Long blogId,String ipAddr) {
        if(blogId == null){
            throw new BusinessException("根据博客ID查询博客信息，博客ID为空");
        }
        //先从redis中获取数据，查询不到再查询数据库
        BlogArticleDTO articleDTO = redisService.getArticleInfo(ipAddr,blogId);
        if(articleDTO != null){
            //判断用户是否收藏和点赞该博客
            this.handleLoginForArticle(userId,articleDTO);
            return new Result<>(articleDTO);
        }
        articleDTO = blogArticleMapper.findArticle(blogId);
        if(articleDTO == null){
            throw new BusinessException("根据博客ID查询博客信息，博客不存在");
        }
        //判断用户是否收藏和点赞该博客
        this.handleLoginForArticle(userId,articleDTO);
        //根据博客ID查询博客分类
        List<BlogTagDTO> classList = blogTagMapper.findBlogTag(blogId);
        articleDTO.setBlogTag(classList);
        //保存数据都redis中
        redisService.saveArticleInfo(ipAddr,articleDTO);
        return new Result<>(articleDTO);
    }
    /**
     * <p>@Description 查询博客信息时判断是否已登录，处理登录后判断用户是否收藏和点赞该博客 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 23:25 </p>
     * @param userId 用户id
     * @param articleDTO 博客信息
     * @return void
     */
    private void handleLoginForArticle(String userId,BlogArticleDTO articleDTO){
        //判断用户是否收藏该博客
        if(StringUtils.isBlank(userId)){
            articleDTO.setCollection(false);
            articleDTO.setPraised(false);
        }else {
            articleDTO.setCollection(blogCollectService.hasCollectBlog(userId,articleDTO.getBlogId()));
            articleDTO.setPraised(blogPraiseService.hasPraise(userId,articleDTO.getBlogId()));
        }
    }
    /**
     * <p>@Description 获取博主博客分类列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.data.dto.response.Result<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public Result<List<TagInfoDTO>> findAuthorBlogTag(String userId, Long blogId) {
        if(StringUtils.isBlank(userId) && blogId == null){
            throw new BusinessException("获取博主博客分类列表，博主ID或博客ID为空");
        }
        if(StringUtils.isBlank(userId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                throw new BusinessException("获取博主博客分类列表，博客ID不存在");
            }
            userId = articleEntity.getUserId();
        }
        List<TagInfoDTO> list = blogTagMapper.findAuthorBlogTag(userId);
        return new Result<>(list);
    }
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.data.dto.response.Result<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public Result<List<BlogGroupDTO>> findAuthorBlogGroup(String userId, Long blogId) {
        if(StringUtils.isBlank(userId) && blogId == null){
            throw new BusinessException("获取博主博客分组列表失败，博主ID或博客ID为空");
        }
        if(StringUtils.isBlank(userId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                throw new BusinessException("获取博主博客分组列表失败，博客ID不存在");
            }
            userId = articleEntity.getUserId();
        }
        List<BlogGroupDTO> list = userBlogGroupMapper.findAuthorBlogGroup(userId);
        return new Result<>(list);
    }
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param queryDTO 查询条件
     * @return com.www.common.data.dto.response.Result<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @Override
    public Result<List<BlogArticleDTO>> findAuthorBlogList(BlogArticleDTO queryDTO) {
        if(queryDTO == null || StringUtils.isBlank(queryDTO.getUserId())){
            throw new BusinessException("获取博主博客列表失败，博主ID为空");
        }
        int pageNum = queryDTO.getPageNum() <= 0 ? 1 : queryDTO.getPageNum();
        long pageSize = queryDTO.getPageSize() <= 0 ? 5 : queryDTO.getPageSize();
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogArticleMapper.findAuthorBlogList(page,queryDTO);
        return new Result<>(queryDTO.getPageNum(),queryDTO.getPageSize(),page.getTotal(),page.getRecords());
    }

    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.data.dto.response.Result<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public Result<List<BlogArticleDTO>> findHotBlogRank() {
        List<BlogArticleDTO> list = blogArticleMapper.findHotBlogRank();
        return new Result<>(list);
    }

    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 当前登录用户ID
     * @param authorId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.data.dto.response.Result<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @Override
    public Result<AuthorDTO> findAuthorInfo(String userId, String authorId, Long blogId) {
        Result<AuthorDTO> response = new Result<>();
        if(StringUtils.isBlank(authorId) && blogId == null){
            throw new BusinessException("查询失败，博主ID或博客ID为空");
        }
        if(StringUtils.isBlank(authorId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                throw new BusinessException("查询失败，博客ID不存在");
            }
            authorId = articleEntity.getUserId();
        }
        UserInfoDTO userInfoDTO = Result.getBackData(baseFeignService.findUserInfo(authorId));
        if(userInfoDTO == null){
            throw new BusinessException("查询失败，博主信息不存在");
        }
        AuthorDTO authorDTO = blogArticleMapper.findAuthorCount(authorId);
        authorDTO = authorDTO != null ? authorDTO : new AuthorDTO().setUserId(userInfoDTO.getUserId());
        authorDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
        //计算码龄
        authorDTO.setAge(DateUtils.getYearsMonths(userInfoDTO.getCreateTime(),DateUtils.getCurrentDateTime()));
        authorDTO.setFlag(StringUtils.isNotBlank(userId) && !StringUtils.equals(userId,authorId));//判断是否展示关注
        QueryWrapper<UserFansEntity> fansWrapper = new QueryWrapper<>();
        fansWrapper.lambda().eq(UserFansEntity::getUserId,authorId);
        fansWrapper.lambda().eq(UserFansEntity::getFansId,userId);
        int count = userFansMapper.selectCount(fansWrapper);
        authorDTO.setFan(count > 0);//判断是否已关注
        return new Result<>(authorDTO);
    }
}
