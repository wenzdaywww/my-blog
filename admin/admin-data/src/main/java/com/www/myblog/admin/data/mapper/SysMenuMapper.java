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
     * <p>@Description 查询所以权限菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 20:43 </p>
     * @return 角色菜单信息
     */
    List<SysRoleMenuDTO> findAllSecurityMenu();
    /**
     * <p>@Description 根据主键删除菜单数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:06 </p>
     * @param menuId 菜单ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long menuId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:06 </p>
     * @param record 菜单数据
     * @return int 插入条数
     */
    int insert(SysMenuEntity record);
    /**
     * <p>@Description 插入数据，只插入非空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:08 </p>
     * @param record 菜单数据
     * @return int 插入条数
     */
    int insertNotNull(SysMenuEntity record);
    /**
     * <p>@Description 根据菜单ID查询菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:09 </p>
     * @param menuId 菜单ID
     * @return com.www.myblog.admin.data.entity.SysMenuEntity 菜单信息
     */
    SysMenuEntity selectByPrimaryKey(Long menuId);
    /**
     * <p>@Description 根据菜单ID更新数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:10 </p>
     * @param record 菜单信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(SysMenuEntity record);
}