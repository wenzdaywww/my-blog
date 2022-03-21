package com.www.myblog.blog.service.browse.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.common.utils.HttpUtils;
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
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findTipBlogList(int pageNum) {
        ResponseDTO<List<BlogArticleDTO>> response = new ResponseDTO<>();
        pageNum = pageNum <= 0 ? 1 : pageNum;
        long pageSize = 5;
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogArticleMapper.findTipBlogList(page);
        List<BlogArticleDTO> blogList =  page.getRecords();
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,blogList);
        return response;
    }

    /**
     * <p>@Description 查询评论列表，包括父评论和子评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 22:34 </p>
     * @param pageNum 页码
     * @param blogId 博客id,不等于null，则是父评论
     * @param parentComId 父评论id，不等于null，则是子评论
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.CommentDTO>>
     */
    @Override
    public ResponseDTO<List<CommentDTO>> findCommentList(int pageNum, Long blogId, Long parentComId) {
        ResponseDTO<List<CommentDTO>> response = new ResponseDTO<>();
        if((blogId == null && parentComId == null) || (blogId != null && parentComId != null)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"查询评论列表失败，信息不全",null);
            return response;
        }
        long pageSize = 5;//分页查询条数
        pageNum = pageNum <= 0 ? 1 : pageNum;
        Page<CommentDTO> page = new Page<>(pageNum,pageSize);
        page = blogCommentMapper.findCommentList(page,blogId,parentComId);
        List<CommentDTO> commentList =  page.getRecords();
        List<String> userIdList = new ArrayList<>();
        //查询不到信息返回
        if(CollectionUtils.isEmpty(commentList)){
            response.setPageNum(pageNum);
            response.setPageSize(pageSize);
            response.setTotalNum(page.getTotal());
            response.setResponse(ResponseDTO.RespEnum.SUCCESS,"未查询评论信息",null);
            return response;
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
            List<UserInfoDTO> userList = ResponseDTO.getBackData(baseFeignService.findUserInfoList(userIdList));
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
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,commentList);
        return response;
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
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public ResponseDTO<BlogArticleDTO> findAriticle(String userId,Long blogId) {
        ResponseDTO<BlogArticleDTO> response = new ResponseDTO<>();
        String ipAddr = HttpUtils.getIp();//IP地址
        if(blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"根据博客ID查询博客信息，博客ID为空",null);
            return response;
        }
        //先从redis中获取数据，查询不到再查询数据库
        BlogArticleDTO articleDTO = redisService.getArticleInfo(ipAddr,blogId);
        if(articleDTO != null){
            //判断用户是否收藏和点赞该博客
            this.handleLoginForArticle(userId,articleDTO);
            response.setResponse(articleDTO);
            return response;
        }
        articleDTO = blogArticleMapper.findArticle(blogId);
        if(articleDTO == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"根据博客ID查询博客信息，博客不存在",null);
            return response;
        }
        //判断用户是否收藏和点赞该博客
        this.handleLoginForArticle(userId,articleDTO);
        //根据博客ID查询博客分类
        List<BlogTagDTO> classList = blogTagMapper.findBlogTag(blogId);
        articleDTO.setBlogTag(classList);
        response.setResponse(articleDTO);
        //保存数据都redis中
        redisService.saveArticleInfo(ipAddr,articleDTO);
        return response;
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
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public ResponseDTO<List<TagInfoDTO>> findAuthorBlogTag(String userId, Long blogId) {
        ResponseDTO<List<TagInfoDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) && blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分类列表，博主ID或博客ID为空",null);
            return response;
        }
        if(StringUtils.isBlank(userId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分类列表，博客ID不存在",null);
                return response;
            }
            userId = articleEntity.getUserId();
        }
        List<TagInfoDTO> list = blogTagMapper.findAuthorBlogTag(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,list);
        return response;
    }
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<List<BlogGroupDTO>> findAuthorBlogGroup(String userId, Long blogId) {
        ResponseDTO<List<BlogGroupDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) && blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分组列表失败，博主ID或博客ID为空",null);
            return response;
        }
        if(StringUtils.isBlank(userId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分组列表失败，博客ID不存在",null);
                return response;
            }
            userId = articleEntity.getUserId();
        }
        List<BlogGroupDTO> list = userBlogGroupMapper.findAuthorBlogGroup(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,list);
        return response;
    }
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param queryDTO 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findAuthorBlogList(BlogArticleDTO queryDTO) {
        ResponseDTO<List<BlogArticleDTO>> response = new ResponseDTO<>();
        if(queryDTO == null || StringUtils.isBlank(queryDTO.getUserId())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客列表失败，博主ID为空",null);
            return response;
        }
        int pageNum = queryDTO.getPageNum() <= 0 ? 1 : queryDTO.getPageNum();
        long pageSize = queryDTO.getPageSize() <= 0 ? 5 : queryDTO.getPageSize();
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogArticleMapper.findAuthorBlogList(page,queryDTO);
        List<BlogArticleDTO> blogList =  page.getRecords();
        response.setPageNum(queryDTO.getPageNum());
        response.setPageSize(queryDTO.getPageSize());
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,blogList);
        return response;
    }

    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findHotBlogRank() {
        List<BlogArticleDTO> list = blogArticleMapper.findHotBlogRank();
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,list);
    }

    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 当前登录用户ID
     * @param authorId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @Override
    public ResponseDTO<AuthorDTO> findAuthorInfo(String userId, String authorId, Long blogId) {
        ResponseDTO<AuthorDTO> responseDTO = new ResponseDTO<>();
        if(StringUtils.isBlank(authorId) && blogId == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，博主ID或博客ID为空",null);
            return responseDTO;
        }
        if(StringUtils.isBlank(authorId)){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，博客ID不存在",null);
                return responseDTO;
            }
            authorId = articleEntity.getUserId();
        }
        UserInfoDTO userInfoDTO = ResponseDTO.getBackData(baseFeignService.findUserInfo(authorId));
        if(userInfoDTO == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，博主信息不存在",null);
            return responseDTO;
        }
        AuthorDTO authorDTO = blogArticleMapper.findAuthorCount(authorId);
        authorDTO = authorDTO != null ? authorDTO : new AuthorDTO().setUserId(userInfoDTO.getUserId());
        authorDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
        //计算码龄
        int month = DateUtils.getMonths(userInfoDTO.getCreateTime(),DateUtils.getCurrentDateTime());
        if(month != -1){
            authorDTO.setAge(month/12 + "年" + month%12 + "个月");
        }else {
            authorDTO.setAge("1个月");
        }
        authorDTO.setFlag(StringUtils.isNotBlank(userId) && !StringUtils.equals(userId,authorId));//判断是否展示关注
        QueryWrapper<UserFansEntity> fansWrapper = new QueryWrapper<>();
        fansWrapper.lambda().eq(UserFansEntity::getUserId,authorId);
        fansWrapper.lambda().eq(UserFansEntity::getFansId,userId);
        int count = userFansMapper.selectCount(fansWrapper);
        authorDTO.setFan(count > 0);//判断是否已关注
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,authorDTO);
        return responseDTO;
    }
}
