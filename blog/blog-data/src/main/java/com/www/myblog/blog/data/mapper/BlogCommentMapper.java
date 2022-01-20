package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogCommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客评论Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogCommentEntity> {
}