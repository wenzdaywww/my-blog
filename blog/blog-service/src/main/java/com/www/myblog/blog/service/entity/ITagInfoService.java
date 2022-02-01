package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.TagInfoEntity;

import java.util.List;

/**
 * <p>@Description 标签信息表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:12 </p>
 */
public interface ITagInfoService {
    /**
     * <p>@Description 根据标签id集合查询标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:39 </p>
     * @param tagIds 标签id集合
     * @return java.util.List<com.www.myblog.blog.data.entity.TagInfoEntity> 标签信息
     */
    List<TagInfoEntity> findByIds(List<Long> tagIds);
}
