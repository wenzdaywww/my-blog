package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客文章Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticleEntity> {
    /**
     * <p>@Description 根据博客ID删除博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:08 </p>
     * @param blogId 博客ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long blogId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:08 </p>
     * @param record 博客信息
     * @return int 插入条数
     */
    int insert(BlogArticleEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:08 </p>
     * @param record 博客信息
     * @return int 插入条数
     */
    int insertNotNull(BlogArticleEntity record);
    /**
     * <p>@Description 根据博客ID查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:08 </p>
     * @param blogId 博客ID
     * @return com.www.myblog.blog.data.entity.BlogArticleEntity 博客信息
     */
    BlogArticleEntity selectByPrimaryKey(Long blogId);
    /**
     * <p>@Description 根据博客ID更新博客信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 23:08 </p>
     * @param record 博客信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(BlogArticleEntity record);
}