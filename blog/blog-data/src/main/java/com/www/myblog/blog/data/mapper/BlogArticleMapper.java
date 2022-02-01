package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 博客文章Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticleEntity> {
    /**
     * <p>@Description 获取推荐博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param page 分页信息
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>>
     */
    Page<BlogArticleDTO> findTipBlogList(Page<BlogArticleDTO> page);
    /**
     * <p>@Description 根据博客ID查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/25 21:21 </p>
     * @param blogId 博客ID
     * @return com.www.myblog.blog.data.dto.BlogArticleDTO
     */
    BlogArticleDTO findArticle(Long blogId);
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param page 分页信息
     * @param queryDTO 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>>
     */
    Page<BlogArticleDTO> findAuthorBlogList(Page<BlogArticleDTO> page, @Param("query") BlogArticleDTO queryDTO);
    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.BlogArticleDTO>
     */
    List<BlogArticleDTO> findHotBlogRank();
    /**
     * <p>@Description 根据博主ID查询博主的相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 16:42 </p>
     * @param userId 博主ID
     * @return com.www.myblog.blog.data.dto.AuthorDTO
     */
    AuthorDTO findAuthorCount(@Param("userId") String userId);
}