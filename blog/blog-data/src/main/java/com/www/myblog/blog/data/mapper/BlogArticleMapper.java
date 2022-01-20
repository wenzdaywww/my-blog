package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.common.pojo.dto.response.ResponseDTO;
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
}