package com.www.myblog.blog.service.user.impl;

import com.www.common.feign.base.IBaseFeignService;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.entity.UserFansEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.entity.IUserFansService;
import com.www.myblog.blog.service.user.IUserBlogService;
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
    private IUserFansService userFansService;

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
