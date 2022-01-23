package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>@Description 博客文章Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:05 </p>
 */
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticleEntity> {
    /**
     * <p>@Description 查询博主的相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 16:42 </p>
     * @param userId 博主ID
     * @return com.www.myblog.blog.data.dto.AuthorDTO
     */
    AuthorDTO findAuthorCount(@Param("userId") String userId);
}