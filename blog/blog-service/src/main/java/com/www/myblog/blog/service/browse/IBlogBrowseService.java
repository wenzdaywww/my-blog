package com.www.myblog.blog.service.browse;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;

/**
 * <p>@Description 匿名用户博客浏览Service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:17 </p>
 */
public interface IBlogBrowseService {
    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 博主ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    ResponseDTO<AuthorDTO> findAuthorInfo(String userId);
}
