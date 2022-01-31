package com.www.myblog.blog.service.user.impl;

import com.www.common.feign.base.IBaseFeignService;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import com.www.myblog.blog.data.entity.UserFansEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.BlogCommentMapper;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.entity.IUserFansService;
import com.www.myblog.blog.service.user.IUserBlogService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private UserFansMapper userFansMapper;
    @Autowired
    private BlogCommentMapper blogCommentMapper;
    @Autowired
    private IUserFansService userFansService;

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
        if(blogId == null){//博客id为空，则是回复评论
            BlogCommentEntity parentCommentEntity = blogCommentMapper.selectById(replyComId);
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
        }else { //博客id不为空，则是新增评论
            BlogArticleEntity articleEntity = blogArticleMapper.selectById(blogId);
            if(articleEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"评论失败，博客不存在",null);
                return response;
            }
        }
        //新增评论
        BlogCommentEntity commentEntity = new BlogCommentEntity();
        commentEntity.setBlogId(blogId).setUserId(userId).setComment(text).setPraise(0L).setParentComId(parentComId).setReplyComId(replyComId);
        commentEntity.setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
        blogCommentMapper.insert(commentEntity);
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
            BlogArticleEntity articleEntity = blogArticleMapper.selectById(blogId);
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
            userFansMapper.insert(fansEntity);
            response.setResponse(ResponseDTO.RespEnum.SUCCESS,true);
        }else {
            userFansMapper.deleteById(fansEntity.getUfId());
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
