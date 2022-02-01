package com.www.myblog.base.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.www.myblog.base.data.entity.SysMenuEntity;

/**
 * <p>@Description SYS_MENU表的基本操作方法接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
public interface ISysMenuService extends IService<SysMenuEntity> {
    /**
     * <p>@Description 新增菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:06 </p>
     * @param menuEntity 菜单信息
     * @return boolean true新增成功，false新增失败
     */
    boolean createEntity(SysMenuEntity menuEntity);
    /**
     * <p>@Description 根据菜单id更新菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:04 </p>
     * @param menuEntity 菜单信息
     * @return boolean true更新成功，false更新失败
     */
    boolean updateEntityById(SysMenuEntity menuEntity);
    /**
     * <p>@Description 根据菜单id删除菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:00 </p>
     * @param menuId 菜单id
     * @return boolean true删除成功，false删除失败
     */
    boolean deleteById(Long menuId);
    /**
     * <p>@Description 根据菜单id查询菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 11:58 </p>
     * @param menuId 菜单id
     * @return com.www.myblog.base.data.entity.SysMenuEntity 菜单信息
     */
    SysMenuEntity findById(Long menuId);
}
