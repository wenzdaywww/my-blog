package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.admin.data.dto.SysMenuDTO;
import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.data.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 菜单表Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:03 </p>
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
    /**
     * <p>@Description 查询所有菜单 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 17:22 </p>
     * @param page 分页信息
     * @param menuDTO 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.www.myblog.admin.data.dto.SysMenuDTO>
     */
    Page<SysMenuDTO> findAllMenu(Page<SysMenuDTO> page, @Param("obj") SysMenuDTO menuDTO);
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return 菜单信息
     */
    List<SysMenuDTO> findUserMenu(@Param("userId") String userId);
    /**
     * <p>@Description 查询所有权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    List<SysRoleMenuDTO> findAllSecurityMenu();
}