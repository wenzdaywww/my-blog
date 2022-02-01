package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.blog.data.mapper.BlogTagMapper;

/**
 * <p>@Description 博客标签表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:08 </p>
 */
public interface IBlogTagService {
    /**
     * <p>@Description 创建博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:31 </p>
     * @param entity 博客标签信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogTagEntity entity);
}
