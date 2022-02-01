package com.www.myblog.blog.service.entity;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.www.myblog.blog.data.entity.BlogArticleEntity;

/**
 * <p>@Description 博客文章service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/29 15:44 </p>
 */
public interface IBlogArticleService {
    /**
     * <p>@Description 更新博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    boolean updateEntity(UpdateWrapper<BlogArticleEntity> wrapper);
    /**
     * <p>@Description 更新博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:30 </p>
     * @param entity 更新内容
     * @param wrapper 更新条件：包括查询条件和更新内容
     * @return boolean true更新成功，false失败
     */
    boolean updateEntity(BlogArticleEntity entity, UpdateWrapper<BlogArticleEntity> wrapper);
    /**
     * <p>@Description 创建博客文章信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:51 </p>
     * @param entity 博客文章
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogArticleEntity entity);
    /**
     * <p>@Description 根据博客id查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:48 </p>
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogArticleEntity 博客信息
     */
    BlogArticleEntity findById(Long blogId);
}
