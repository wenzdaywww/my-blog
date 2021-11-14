package com.www.myblog.admin.service;

import com.www.myblog.admin.data.entity.SysUserEntity;

/**
 * <p>@Description 用户表service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:31 </p>
 */
public interface ISysUserService {
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 15:32 </p>
     * @param userId 用户ID
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    SysUserEntity findUserById(String userId);
}
