package com.www.myblog.blog.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.constants.CommenConstant;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.CollectGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.entity.BlogCollectEntity;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import com.www.myblog.blog.data.entity.BlogPraiseEntity;
import com.www.myblog.blog.data.entity.CollectGroupEntity;
import com.www.myblog.blog.data.entity.UserFansEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.BlogCollectMapper;
import com.www.myblog.blog.data.mapper.CollectGroupMapper;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.entity.IBlogArticleService;
import com.www.myblog.blog.service.entity.IBlogCollectService;
import com.www.myblog.blog.service.entity.IBlogCommentService;
import com.www.myblog.blog.service.entity.IBlogPraiseService;
import com.www.myblog.blog.service.entity.ICollectGroupService;
import com.www.myblog.blog.service.entity.IUserFansService;
import com.www.myblog.blog.service.redis.IRedisService;
import com.www.myblog.blog.service.user.IUserBlogService;
import com.www.myblog.common.config.feign.base.IBaseFeignService;
import com.www.myblog.common.dto.BlogArticleDTO;
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
 * <p>@Description 当前登录用户博客信息service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:21 </p>
 */
@Service
public class UserBlogServiceImpl implements IUserBlogService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private IBaseFeignService baseFeignService;
    @Autowired
    private IUserFansService userFansService;
    @Autowired
    private IBlogCollectService blogCollectService;
    @Autowired
    private IBlogArticleService blogArticleService;
    @Autowired
    private IBlogCommentService blogCommentService;
    @Autowired
    private UserFansMapper userFansMapper;
    @Autowired
    private ICollectGroupService collectGroupService;
    @Autowired
    private CollectGroupMapper collectGroupMapper;
    @Autowired
    private BlogCollectMapper blogCollectMapper;
    @Autowired
    private IBlogPraiseService blogPraiseService;
    @Autowired
    private IRedisService redisService;


    /**
     * <p>@Description 点赞或取消点赞 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:24 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true点赞，fasle取消点赞
     */
    @Override
    public ResponseDTO<Boolean> savePraiseInfo(String userId, Long blogId) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) || blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"点赞或取消点赞失败，信息不全",null);
            return response;
        }
        BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
        if(articleEntity == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"点赞或取消点赞失败，博客不存在",null);
            return response;
        }
        if(StringUtils.equals(userId,articleEntity.getUserId())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"点赞或取消点赞失败，不能点赞自己的博客",null);
            return response;
        }
        BlogPraiseEntity praiseEntity = blogPraiseService.findEntity(userId,blogId);
        //点赞
        if(praiseEntity == null){
            praiseEntity = new BlogPraiseEntity();
            praiseEntity.setBlogId(blogId).setUserId(userId)
                    .setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
            blogPraiseService.createEntity(praiseEntity);
            response.setResponse(true);
            //更新博客点赞量
            redisService.updateBlogNum(CommenConstant.PRAISE, blogId,true);
            return response;
        }else {//取消点赞
            blogPraiseService.deleteEntity(praiseEntity.getBpId());
            response.setResponse(false);
            //更新博客点赞量
            redisService.updateBlogNum(CommenConstant.PRAISE, blogId,false);
            return response;
        }
    }
    /**
     * <p>@Description 修改博客收藏夹位置 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:45 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @param cgId   收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean> true修改成功，false修改失败
     */
    @Override
    public ResponseDTO<Boolean> updateCollectId(String userId, Long blogId, Long cgId) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) || blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"修改博客收藏夹位置失败，信息不全",null);
            return response;
        }
        BlogCollectEntity collectEntity = blogCollectService.findBlogCollectEntity(userId,blogId);
        if(collectEntity == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"修改博客收藏夹位置失败，博客收藏不存在",null);
            return response;
        }
        UpdateWrapper<BlogCollectEntity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(BlogCollectEntity::getCollectId,collectEntity.getCollectId());
        wrapper.lambda().set(BlogCollectEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        if(cgId == null){
            wrapper.lambda().set(BlogCollectEntity::getCgId,null);
        }else {
            CollectGroupEntity groupEntity = collectGroupService.findById(cgId);
            if(groupEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"修改博客收藏夹位置失败，收藏夹不存在",null);
                return response;
            }
            wrapper.lambda().set(BlogCollectEntity::getCgId,cgId);
        }
        boolean isOk = blogCollectService.updateEntity(wrapper);
        response.setResponse(isOk);
        return response;
    }
    /**
     * <p>@Description 查询用户的博客收藏列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 19:08 </p>
     * @param query 查询条件就
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>> 博客收藏列表
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findCollectList(CollectGroupDTO query) {
        ResponseDTO<List<BlogArticleDTO>> response = new ResponseDTO<>();
        if(query == null || StringUtils.isBlank(query.getUserId())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"查询用户的博客收藏列表失败，信息不全",null);
            return response;
        }
        int pageNum = query.getPageNum() <= 0 ? 1 : query.getPageNum();
        long pageSize = query.getPageSize() <= 0 ? 5 : query.getPageSize();
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogCollectMapper.findCollectList(page,query.getUserId(),query.getCgId());
        List<BlogArticleDTO> blogList = page.getRecords();
        response.setPageNum(query.getPageNum());
        response.setPageSize(query.getPageSize());
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,blogList);
        return response;
    }
    /**
     * <p>@Description 查询收藏夹列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:29 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    @Override
    public ResponseDTO<List<CollectGroupDTO>> findCollectGroup(String userId) {
        ResponseDTO<List<CollectGroupDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"查询收藏夹列表失败，信息不全",null);
            return response;
        }
        List<CollectGroupDTO> list = collectGroupMapper.findCollectGroup(userId);
        response.setResponse(list);
        return response;
    }
    /**
     * <p>@Description 新增收藏夹 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:31 </p>
     * @param userId 用户id
     * @param collectName 收藏夹名称
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    @Override
    public ResponseDTO<Boolean> addCollectGroup(String userId, String collectName) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        if (StringUtils.isAnyBlank(userId, collectName)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"新增收藏夹失败，信息不全",null);
            return response;
        }
        CollectGroupEntity collectGroupEntity = new CollectGroupEntity();
        collectGroupEntity.setUserId(userId).setCollectName(collectName).
                setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,collectGroupService.createEntity(collectGroupEntity));
        return response;
    }
    /**
     * <p>@Description  获取粉丝列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:54 </p>
     * @param pageNum 页码
     * @param userId  用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>> 关注用户列表
     */
    @Override
    public ResponseDTO<List<AuthorDTO>> findFansList(int pageNum, String userId) {
        ResponseDTO<List<AuthorDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) || pageNum < 0){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取粉丝列表失败。信息不全",null);
            return response;
        }
        long pageSize = 10;
        pageNum = pageNum <= 0 ? 1 : pageNum;
        Page<AuthorDTO> page = new Page<>(pageNum,pageSize);
        page = userFansMapper.findFansList(page,userId);
        List<AuthorDTO> followList =  page.getRecords();
        if(CollectionUtils.isNotEmpty(followList)){
            List<String> userIdList = followList.stream().map(AuthorDTO::getFansId).collect(Collectors.toList());
            List<UserInfoDTO> userList = ResponseDTO.getBackData(baseFeignService.findUserInfoList(userIdList));
            Map<String,UserInfoDTO> userMap = CollectionUtils.isEmpty(userList) ? new HashMap<>() :
                    userList.stream().collect(Collectors.toMap(UserInfoDTO::getUserId, Function.identity(), (key1, key2) -> key2));
            for (AuthorDTO authorDTO : followList){
                UserInfoDTO userInfoDTO = userMap.get(authorDTO.getFansId());
                if(userInfoDTO != null){
                    authorDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto()).setBrief(userInfoDTO.getBrief());
                }
                authorDTO.setFan(StringUtils.isNotBlank(authorDTO.getUserId()));
            }
        }
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,followList);
        return response;
    }
    /**
     * <p>@Description  获取关注列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:54 </p>
     * @param pageNum 页码
     * @param userId  用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>> 关注用户列表
     */
    @Override
    public ResponseDTO<List<AuthorDTO>> findFollowList(int pageNum, String userId) {
        ResponseDTO<List<AuthorDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) || pageNum < 0){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取关注列表失败。信息不全",null);
            return response;
        }
        long pageSize = 10;
        pageNum = pageNum <= 0 ? 1 : pageNum;
        Page<AuthorDTO> page = new Page<>(pageNum,pageSize);
        page = userFansMapper.findFollowList(page,userId);
        List<AuthorDTO> followList =  page.getRecords();
        if(CollectionUtils.isNotEmpty(followList)){
            List<String> userIdList = followList.stream().map(AuthorDTO::getUserId).collect(Collectors.toList());
            List<UserInfoDTO> userList = ResponseDTO.getBackData(baseFeignService.findUserInfoList(userIdList));
            Map<String,UserInfoDTO> userMap = CollectionUtils.isEmpty(userList) ? new HashMap<>() :
                    userList.stream().collect(Collectors.toMap(UserInfoDTO::getUserId, Function.identity(), (key1, key2) -> key2));
            for (AuthorDTO authorDTO : followList){
                UserInfoDTO userInfoDTO = userMap.get(authorDTO.getUserId());
                if(userInfoDTO != null){
                    authorDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto()).setBrief(userInfoDTO.getBrief());
                }
                authorDTO.setFan(true);
            }
        }
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,followList);
        return response;
    }
    /**
     * <p>@Description 添加/取消博客收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:45 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @param cgId 收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean> true添加收藏，false取消收藏
     */
    @Override
    public ResponseDTO<BlogArticleDTO> addCollect(String userId, Long blogId,Long cgId) {
        ResponseDTO<BlogArticleDTO> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId) || blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"添加/取消博客收藏失败，信息不全",null);
            return response;
        }
        BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
        if(articleEntity == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"添加/取消博客收藏失败，博客不存在",null);
            return response;
        }
        if(StringUtils.equals(userId,articleEntity.getUserId())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"添加/取消博客收藏失败，不能收藏自己的博客",null);
            return response;
        }
        BlogCollectEntity collectEntity = blogCollectService.findBlogCollectEntity(userId,blogId);
        BlogArticleDTO articleDTO = new BlogArticleDTO();
        //新增收藏
        if (collectEntity == null){
            if(cgId != null){
                CollectGroupEntity groupEntity = collectGroupService.findById(cgId);
                if(groupEntity == null){
                    response.setResponse(ResponseDTO.RespEnum.FAIL,"博客收藏失败，收藏夹不存在",null);
                    return response;
                }
            }
            collectEntity = new BlogCollectEntity();
            collectEntity.setUserId(userId).setBlogId(blogId).setCgId(cgId)
                    .setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
            blogCollectService.createBlogCollectEntity(collectEntity);
            articleDTO.setCollection(true);//已收藏
            //更新博客收藏量
            redisService.updateBlogNum(CommenConstant.COLLECT, blogId,true);
        }else {//取消收藏
            articleDTO.setCollection(false);//未收藏
            //更新博客收藏量
            redisService.updateBlogNum(CommenConstant.COLLECT,blogId,false);
            blogCollectService.deleteBlogCollectEntity(collectEntity.getCollectId());
        }
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,articleDTO);
        return response;
    }

    /**
     * <p>@Description 新增评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 21:13 </p>
     * @param userId 用户ID
     * @param blogId 博客ID，不等于null，则是新增的评论
     * @param replyComId 回复的评论ID，不等于null，则是回复评论
     * @param text   评论内容
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.CommentDTO>
     */
    @Override
    public ResponseDTO<CommentDTO> addBlogComment(String userId, Long blogId, Long replyComId, String text) {
        ResponseDTO<CommentDTO> response = new ResponseDTO<>();
        if(StringUtils.isAnyBlank(userId,text) || (blogId == null && replyComId == null) || (blogId != null && replyComId != null)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"评论失败，信息不全",null);
            return response;
        }
        Long parentComId = null;//父评论id
        String replyUserId = null;//回复评论人ID
        if(blogId == null){//博客id为空，则是回复评论,博客id不为空，则是新增评论
            BlogCommentEntity parentCommentEntity = blogCommentService.findById(replyComId);
            if(parentCommentEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"评论失败，评论不存在",null);
                return response;
            }
            replyUserId = parentCommentEntity.getUserId();
            //回复的评论的父评论id为空，则说明改评论是父评论
            if(parentCommentEntity.getParentComId() == null){
                parentComId = replyComId;
                replyComId = null;
            }else {//回复的评论的父评论id不为空，则说明改评论是子评论
                parentComId = parentCommentEntity.getParentComId();
            }
            blogId = parentCommentEntity.getBlogId();
        }
        BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
        if(articleEntity == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"评论失败，博客不存在",null);
            return response;
        }
        //新增评论
        BlogCommentEntity commentEntity = new BlogCommentEntity();
        commentEntity.setBlogId(blogId).setUserId(userId).setComment(text).setPraise(0L).setParentComId(parentComId).setReplyComId(replyComId);
        commentEntity.setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
        blogCommentService.createEntity(commentEntity);
        //更新博客评论数量
        redisService.updateBlogNum(CommenConstant.COMMENT, blogId,true);
        //返回评论信息
        CommentDTO commentDTO = new CommentDTO();
        //查询用户信息
        if(StringUtils.isNotBlank(replyUserId)){
            //查询评论人和回复人用户信息
            List<String> userIdList = new ArrayList<>();
            userIdList.add(userId);
            userIdList.add(replyUserId);
            List<UserInfoDTO> userList = ResponseDTO.getBackData(baseFeignService.findUserInfoList(userIdList));
            if(CollectionUtils.isNotEmpty(userList)){
                for (UserInfoDTO userInfoDTO : userList){
                    if(StringUtils.equals(userInfoDTO.getUserId(),replyUserId)){
                        commentDTO.setReplyName(userInfoDTO.getUserName());//设置回复人名称
                    }
                    if(StringUtils.equals(userInfoDTO.getUserId(),userId)){//设置评论人名称和头像
                        commentDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
                    }
                }
            }
        }else {
            UserInfoDTO userInfoDTO = ResponseDTO.getBackData(baseFeignService.findUserInfo(userId));
            if(userInfoDTO != null){//设置评论人名称和头像
                commentDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
            }
        }
        commentDTO.setBlogId(blogId).setCommentId(commentEntity.getCommentId())
                .setComment(text).setUserId(userId).setMore(0L).setPraise(0L).setOpen(false)
                .setCreateDate(DateUtils.format(commentEntity.getCreateTime(), DateUtils.DateFormatEnum.YYYY_MM_DD));
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,commentDTO);
        return response;
    }

    /**
     * <p>@Description 关注或取消关注博主 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 15:16 </p>
     * @param userId 当前登录的用户ID
     * @param authorId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean>
     */
    @Override
    public ResponseDTO<Boolean> followAuthor(String userId, String authorId,Long blogId) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        if(StringUtils.isBlank(authorId) && blogId == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"关注博主,博客ID或博主ID为空",null);
            return response;
        }
        if(StringUtils.isNotBlank(authorId)){
            List<String> reqList = new ArrayList<>();
            reqList.add(authorId);
            Boolean isExist = ResponseDTO.getBackData(baseFeignService.validateUserExist(reqList));
            if(!isExist){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"关注博主,博主ID不存在",null);
                return response;
            }
        }else if(blogId != null){
            BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
            if(articleEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"关注博主，博客ID不存在",null);
                return response;
            }
            authorId = articleEntity.getUserId();
        }
        UserFansEntity fansEntity = userFansService.findUserFansEntity(authorId,userId);
        if(fansEntity == null){
            fansEntity = new UserFansEntity();
            fansEntity.setUserId(authorId).setFansId(userId);
            fansEntity.setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
            userFansService.createEntity(fansEntity);
            response.setResponse(ResponseDTO.RespEnum.SUCCESS,true);
        }else {
            userFansService.deleteEntity(fansEntity.getUfId());
            response.setResponse(ResponseDTO.RespEnum.SUCCESS,false);
        }
        return response;
    }

    /**
     * <p>@Description 获取用户博客相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 18:23 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @Override
    public ResponseDTO<AuthorDTO> findUserCount(String userId) {
        ResponseDTO<AuthorDTO> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取用户博客相关统计信息失败，用户ID为空",null);
            return response;
        }
        AuthorDTO authorDTO = blogArticleMapper.findAuthorCount(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,authorDTO);
        return response;
    }
}
