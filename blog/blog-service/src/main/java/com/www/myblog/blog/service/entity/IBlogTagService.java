package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.blog.data.mapper.BlogTagMapper;

import java.util.List;

/**
 * <p>@Description 博客标签表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:08 </p>
 */
public interface IBlogTagService {
    /**
     * <p>@Description 批量创建博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:51 </p>
     * @param tagList 博客标签信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntityBatch(List<BlogTagEntity> tagList);
    /**
     * <p>@Description 根据博客标签id删除所有博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:34 </p>
     * @param btIds 博客标签id
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteById(List<Long> btIds);
    /**
     * <p>@Description 根据博客id删除所有博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:34 </p>
     * @param blogId 博客id
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteByBlogId(Long blogId);
    /**
     * <p>@Description 根据博客id查询标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 15:07 </p>
     * @param blogId 博客id
     * @return java.util.List<com.www.myblog.blog.data.entity.BlogTagEntity> 标签信息
     */
    List<BlogTagEntity> findEntityByBlogId(Long blogId);
    /**
     * <p>@Description 创建博客标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:31 </p>
     * @param entity 博客标签信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(BlogTagEntity entity);
}
