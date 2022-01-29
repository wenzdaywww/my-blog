package com.www.myblog.blog.service.edit;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>@Description 博客编辑service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 18:29 </p>
 */
public interface IEditBlogService {
    /**
     * <p>@Description 当前登录的用户创建博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param blog 博客信息
     * @param img 博客封面图片
     * @return com.www.common.pojo.dto.response.ResponseDTO<String> 博客文章主键
     */
    ResponseDTO<Long> createBlogArticle(BlogArticleDTO blog, MultipartFile img);
    /**
     * <p>@Description 当前登录用户新增分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @param name 分组名称
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    ResponseDTO<String> createBlogGroup(String userId,String name);
    /**
     * <p>@Description 查询用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    ResponseDTO<List<BlogGroupDTO>> findBlogGroup(String userId);
}
