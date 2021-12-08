package com.www.myblog.admin.service.entity;

import com.www.myblog.admin.data.entity.SysUserEntity;

/**
 * <p>@Description SYS_USER表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysUserService {
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param userId 用户ID
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    SysUserEntity findUserEntityById(String userId);
}
