package com.www.myblog.blog.service.browse;

import com.www.common.pojo.dto.redis.BlogArticleDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;

import java.util.List;

/**
 * <p>@Description 匿名用户博客浏览Service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:17 </p>
 */
public interface IBlogBrowseService {
    /**
     * <p>@Description 获取推荐博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param pageNum 页码
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.common.pojo.dto.redis.BlogArticleDTO>>
     */
    ResponseDTO<List<BlogArticleDTO>> findTipBlogList(int pageNum);
    /**
     * <p>@Description 查询评论列表，包括父评论和子评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 22:34 </p>
     * @param pageNum 页码
     * @param blogId 博客id,不等于null，则是父评论
     * @param parentComId 父评论id，不等于null，则是子评论
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.CommentDTO>>
     */
    ResponseDTO<List<CommentDTO>> findCommentList(int pageNum,Long blogId, Long parentComId);
    /**
     * <p>@Description 根据博客ID查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/25 21:21 </p>
     * @param userId 当前登录用户ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.redis.BlogArticleDTO>
     */
    ResponseDTO<BlogArticleDTO> findAriticle(String userId,Long blogId);
    /**
     * <p>@Description 获取博主博客分类列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    ResponseDTO<List<TagInfoDTO>> findAuthorBlogTag(String userId, Long blogId);
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    ResponseDTO<List<BlogGroupDTO>> findAuthorBlogGroup(String userId,Long blogId);
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param queryDTO 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.common.pojo.dto.redis.BlogArticleDTO>>
     */
    ResponseDTO<List<BlogArticleDTO>> findAuthorBlogList(BlogArticleDTO queryDTO);
    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 当前登录用户ID
     * @param authorId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    ResponseDTO<AuthorDTO> findAuthorInfo(String userId,String authorId,Long blogId);
    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.redis.BlogArticleDTO>
     */
    ResponseDTO<List<BlogArticleDTO>> findHotBlogRank();
}
