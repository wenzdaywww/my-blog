package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogPraiseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客点赞信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogPraiseMapper extends BaseMapper<BlogPraiseEntity> {
}