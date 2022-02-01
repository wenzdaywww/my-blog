package com.www.myblog.base.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.www.myblog.base.data.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * <p>@Description SYS_ROLE_MENU表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysRoleMenuService extends IService<SysRoleMenuEntity> {
    /**
     * <p>@Description 根据主键id集合删除角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:17 </p>
     * @param idList 主键id集合
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteByIds(List<Long> idList);
    /**
     * <p>@Description 根据菜单id查询角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:14 </p>
     * @param menuId 菜单id
     * @return java.util.List<com.www.myblog.base.data.entity.SysRoleMenuEntity> 角色菜单信息
     */
    List<SysRoleMenuEntity> findEntityByMenuId(Long menuId);
    /**
     * <p>@Description 根据菜单id删除角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:10 </p>
     * @param menuId 菜单id
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteByMenuId(Long menuId);
}
