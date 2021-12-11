package com.www.myblog.admin.service.menu;

import com.www.myblog.admin.data.dto.SysMenuDTO;
import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.common.pojo.ResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>@Description 菜单service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:41 </p>
 */
public interface IMenuInfoService {
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> updateOrSave(SysMenuDTO menu);
    /**
     * <p>@Description 查询所有权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    List<SysRoleMenuDTO> findAllSecurityMenu();
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 16:59 </p>
     * @param menuDTO 查询条件
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysMenuDTO>>
     */
    ResponseDTO<List<SysMenuDTO>> findAllMenu(SysMenuDTO menuDTO,int pageNum, int pageSize);
}