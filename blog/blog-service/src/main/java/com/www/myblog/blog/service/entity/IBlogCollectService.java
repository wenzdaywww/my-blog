package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogCollectEntity;

/**
 * <p>@Description 博客收藏表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/29 15:44 </p>
 */
public interface IBlogCollectService {
    /**
     * <p>@Description 判断该用户是否已收藏该博客 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:16 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return boolean true已收藏，false未收藏
     */
    boolean hasCollectBlog(String userId,Long blogId);
    /**
     * <p>@Description 新增博客收藏信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:05 </p>
     * @param collectEntity 博客收藏信息
     * @return true新增成功，false新增失败
     */
    boolean createBlogCollectEntity(BlogCollectEntity collectEntity);
    /**
     * <p>@Description 查询博客收藏信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:53 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.myblog.blog.data.entity.BlogCollectEntity
     */
    BlogCollectEntity findBlogCollectEntity(String userId,Long blogId);
    /**
     * <p>@Description 删除博客收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:00 </p>
     * @param collectId 博客收藏id
     * @return boolean true删除成功，false失败
     */
    boolean deleteBlogCollectEntity(Long collectId);
}
