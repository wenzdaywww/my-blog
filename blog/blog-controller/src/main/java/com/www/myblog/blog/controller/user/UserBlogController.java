package com.www.myblog.blog.controller.user;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.constant.AuthorityContant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.service.user.IUserBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>@Description 当前登录用户博客信息controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:14 </p>
 */
@RestController
@RequestMapping("user")
@PreAuthorize(AuthorityContant.ALL)
public class UserBlogController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IUserBlogService userBlogService;

    /**
     * <p>@Description 获取用户博客相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 18:18 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @GetMapping("count")
    public ResponseDTO<AuthorDTO> findUserCount(){
        return userBlogService.findUserCount(jwtTokenConverter.getUserId());
    }
}
