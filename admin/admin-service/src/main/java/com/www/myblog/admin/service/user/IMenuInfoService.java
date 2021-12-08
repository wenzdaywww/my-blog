package com.www.myblog.admin.service.user;

import com.www.myblog.admin.data.dto.SysRoleMenuDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>@Description 角色菜单service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:41 </p>
 */
public interface IMenuInfoService {
    /**
     * <p>@Description 查询所以权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    List<SysRoleMenuDTO> findAllSecurityMenu();
}
