package com.www.myblog.blog.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.blog.data.entity.BlogClassEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 博客分类信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Mapper
public interface BlogClassMapper extends BaseMapper<BlogClassEntity> {
}