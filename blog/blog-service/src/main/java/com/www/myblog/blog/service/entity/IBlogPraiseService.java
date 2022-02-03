package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogPraiseEntity;

/**
 * <p>@Description 博客点赞表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:08 </p>
 */
public interface IBlogPraiseService {
    /**
     * <p>@Description 是否已经点赞该博客 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:39 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return boolean true已赞，false未赞
     */
    boolean hasPraise(String userId, Long blogId);
    /**
     * <p>@Description 查询用户对博客的点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogPaiseEntity
     */
    BlogPraiseEntity findEntity(String userId, Long blogId);
    /**
     * <p>@Description 创建博客点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param entity 博客点赞信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogPraiseEntity entity);
    /**
     * <p>@Description 删除博客点赞信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:28 </p>
     * @param bpId 博客点赞主键
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteEntity(Long bpId);
}
