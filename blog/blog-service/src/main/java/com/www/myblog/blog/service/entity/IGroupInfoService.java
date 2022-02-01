package com.www.myblog.blog.service.entity;

import com.www.myblog.blog.data.entity.GroupInfoEntity;

/**
 * <p>@Description 分组信息表service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/1 13:12 </p>
 */
public interface IGroupInfoService {
    /**
     * <p>@Description 创建分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:36 </p>
     * @param entity 分组信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(GroupInfoEntity entity);
    /**
     * <p>@Description 根据主键查询分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 13:34 </p>
     * @param groupId 分组主键
     * @return com.www.myblog.blog.data.entity.GroupInfoEntity 分组信息
     */
    GroupInfoEntity findById(Long groupId);
}
