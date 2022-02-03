package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.CollectGroupEntity;

/**
 * <p>@Description 收藏分组信息表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:12 </p>
 */
public interface ICollectGroupService {
    /**
     * <p>@Description  新增收藏分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:33 </p>
     * @param entity 收藏分组信息
     * @return boolean true新增成功，false新增失败
     */
    boolean createEntity(CollectGroupEntity entity);
    /**
     * <p>@Description 根据主键查询收藏分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:23 </p>
     * @param cgId 收藏分组id
     * @return com.www.myblog.blog.data.entity.CollectGroupEntity 收藏分组信息
     */
    CollectGroupEntity findById(Long cgId);
}
