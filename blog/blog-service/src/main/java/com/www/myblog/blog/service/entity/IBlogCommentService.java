package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogCommentEntity;

/**
 * <p>@Description 博客评论表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:01 </p>
 */
public interface IBlogCommentService {
    /**
     * <p>@Description 根据主键查询博客评论信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:06 </p>
     * @param commentId 博客评论id
     * @return com.www.myblog.blog.data.entity.BlogCommentEntity 博客评论信息
     */
    BlogCommentEntity findById(Long commentId);
    /**
     * <p>@Description 创建博客评论信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:03 </p>
     * @param entity 博客评论信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogCommentEntity entity);
}
