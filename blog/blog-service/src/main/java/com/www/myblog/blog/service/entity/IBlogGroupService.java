package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogGroupEntity;

/**
 * <p>@Description 博客分组表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:08 </p>
 */
public interface IBlogGroupService {
    /**
     * <p>@Description 创建博客分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:27 </p>
     * @param entity 博客分组信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogGroupEntity entity);
}
