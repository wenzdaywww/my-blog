package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 角色菜单表Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {
    /**
     * <p>@Description 根据主键删除角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:31 </p>
     * @param srmId 角色菜单ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long srmId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:32 </p>
     * @param record 角色菜单信息
     * @return int 插入条数
     */
    int insert(SysRoleMenuEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:32 </p>
     * @param record 角色菜单信息
     * @return int 插入条数
     */
    int insertNotNull(SysRoleMenuEntity record);
    /**
     * <p>@Description 根据主键查询角色菜单信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:33 </p>
     * @param srmId 角色菜单ID
     * @return com.www.myblog.admin.data.entity.SysRoleMenuEntity 角色菜单信息
     */
    SysRoleMenuEntity selectByPrimaryKey(Long srmId);
    /**
     * <p>@Description 根据主键更新角色菜单信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:34 </p>
     * @param record 角色菜单信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(SysRoleMenuEntity record);
}