package com.www.myblog.blog.service.user.impl;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.UserFansMapper;
import com.www.myblog.blog.service.user.IUserBlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserFansMapper userFansMapper;

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
