package com.www.myblog.admin.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.www.myblog.admin.data.entity.SysRoleEntity;

import java.util.List;

/**
 * <p>@Description SYS_ROLE表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysRoleService extends IService<SysRoleEntity> {
    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleCode 角色名称
     * @return  角色信息
     */
    List<SysRoleEntity> findRoleEntityByName(String... roleCode);
    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleCode 角色名称
     * @return com.www.myblog.admin.data.entity.SysUserEntity 角色信息
     */
    SysRoleEntity findRoleEntityByName(String roleCode);
}
