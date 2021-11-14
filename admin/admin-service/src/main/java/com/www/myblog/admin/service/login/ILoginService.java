package com.www.myblog.admin.service.login;

import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.common.pojo.ResponseDTO;

/**
 * <p>@Description 用户登录service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 14:32 </p>
 */
public interface ILoginService {
    /**
     * <p>@Description 用户登入 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 14:33 </p>
     * @param userId 用户ID
     * @param password 密码
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    ResponseDTO<SysUserEntity> userLogin(String userId, String password);
}
