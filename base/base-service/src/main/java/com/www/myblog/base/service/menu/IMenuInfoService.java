package com.www.myblog.base.service.menu;

import com.www.common.data.response.Result;
import com.www.myblog.base.data.dto.SysMenuDTO;

import java.util.List;

/**
 * <p>@Description 菜单service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 20:41 </p>
 */
public interface IMenuInfoService {
    /**
     * <p>@Description 删除菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 23:57 </p>
     * @param menuId 菜单ID
     * @return Response<String>
     */
    Result<String> deleteMenu(Long menuId);
    /**
     * <p>@Description 修改或创建菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 19:58 </p>
     * @param menu 菜单信息
     * @return Response<java.lang.String>
     */
    Result<String> updateOrSave(SysMenuDTO menu);
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 16:59 </p>
     * @param menuDTO 查询条件
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return Response<java.util.List < com.www.myblog.base.data.dto.SysMenuDTO>>
     */
    Result<List<SysMenuDTO>> findAllMenu(SysMenuDTO menuDTO,int pageNum, long pageSize);
}
