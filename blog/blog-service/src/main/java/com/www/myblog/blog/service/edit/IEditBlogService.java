package com.www.myblog.blog.service.edit;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
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
     * <p>@Description 修改博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     * @param blog 博客信息
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.BlogArticleDTO>
     */
    ResponseDTO<Boolean> updateBlogTagAndGroup(BlogArticleDTO blog);
    /**
     * <p>@Description 查询博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     * @param blogId 博客id
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.BlogArticleDTO>
     */
    ResponseDTO<BlogArticleDTO> findBlogTagAndGroup(Long blogId);
    /**
     * <p>@Description 查询所有博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    ResponseDTO<List<TagInfoDTO>> findAllBlogTag();
    /**
     * <p>@Description 获取用户博客标签列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    ResponseDTO<List<TagInfoDTO>> findUserBlogTag(String userId);
    /**
     * <p>@Description 获取博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param query 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>>
     */
    ResponseDTO<List<BlogArticleDTO>> findBlogList(BlogArticleDTO query);
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
