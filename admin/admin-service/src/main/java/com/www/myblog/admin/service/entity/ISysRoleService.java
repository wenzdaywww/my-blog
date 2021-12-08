package com.www.myblog.admin.service.entity;

import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;

/**
 * <p>@Description SYS_ROLE表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysRoleService {
    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleName 角色名称
     * @return com.www.myblog.admin.data.entity.SysUserEntity 角色信息
     */
    SysRoleEntity findRoleEntityByName(String roleName);
}