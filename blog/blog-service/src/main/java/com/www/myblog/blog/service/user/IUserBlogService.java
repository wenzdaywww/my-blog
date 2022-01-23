package com.www.myblog.blog.service.user;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>@Description 当前登录用户博客信息service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:21 </p>
 */
public interface IUserBlogService {
    /**
     * <p>@Description 获取用户博客相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 18:23 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    ResponseDTO<AuthorDTO> findUserCount(String userId);
}
