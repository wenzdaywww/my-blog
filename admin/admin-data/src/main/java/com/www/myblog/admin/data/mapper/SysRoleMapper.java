package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description 角色表Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    /**
     * <p>@Description 根据角色ID删除角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:26 </p>
     * @param roleId 角色ID
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long roleId);
    /**
     * <p>@Description 插入角色信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:27 </p>
     * @param record 角色信息
     * @return int 插入条数
     */
    int insert(SysRoleEntity record);
    /**
     * <p>@Description 插入角色信息，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:27 </p>
     * @param record 角色信息
     * @return int 插入条数
     */
    int insertNotNull(SysRoleEntity record);
    /**
     * <p>@Description 根据角色ID查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:28 </p>
     * @param roleId 角色ID
     * @return com.www.myblog.admin.data.entity.SysRoleEntity 角色信息
     */
    SysRoleEntity selectByPrimaryKey(Long roleId);
    /**
     * <p>@Description 根据角色ID更新角色信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:28 </p>
     * @param record 角色信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(SysRoleEntity record);
}