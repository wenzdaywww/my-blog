package com.www.myblog.base.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.www.myblog.base.data.entity.SysUserRoleEntity;

/**
 * <p>@Description SYS_USER_ROLE表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysUserRoleService extends IService<SysUserRoleEntity> {
    /**
     * <p>@Description 创建用户角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:39 </p>
     * @param entity 用户角色信息
     * @return boolean true创建成功，false创建失败
     */
    boolean createEntity(SysUserRoleEntity entity);
}
