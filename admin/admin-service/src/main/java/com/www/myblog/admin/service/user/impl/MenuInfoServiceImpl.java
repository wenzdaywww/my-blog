package com.www.myblog.admin.service.user.impl;

import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.data.mapper.SysMenuMapper;
import com.www.myblog.admin.service.user.IMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description 角色菜单service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:42 </p>
 */
@Service
public class MenuInfoServiceImpl implements IMenuInfoService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * <p>@Description 查询所以权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    @Override
    public List<SysRoleMenuDTO> findAllSecurityMenu() {
        return sysMenuMapper.findAllSecurityMenu();
    }
}
