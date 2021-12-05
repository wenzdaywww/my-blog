package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.data.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

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
     * <p>@Description 查询所有权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    List<SysRoleMenuDTO> findAllSecurityMenu();
}