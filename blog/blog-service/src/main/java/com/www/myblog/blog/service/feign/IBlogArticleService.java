package com.www.myblog.blog.service.feign;

import com.www.common.pojo.dto.response.ResponseDTO;

/**
 * <p>@Description 对外博客文章服务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:17 </p>
 */
public interface IBlogArticleService {
    /**
     * <p>@Description 查询用户的博客数量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/20 21:16 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    public ResponseDTO<Integer> findUserBlogNum(String userId);
}
