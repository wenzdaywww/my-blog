package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {
    /**
     * <p>@Description 根据主键删除数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:42 </p>
     * @param surId 主键
     * @return int 删除条数
     */
    int deleteByPrimaryKey(Long surId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:42 </p>
     * @param record 用户角色信息
     * @return int 插入条数
     */
    int insert(SysUserRoleEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:42 </p>
     * @param record 用户角色信息
     * @return int 插入条数
     */
    int insertNotNull(SysUserRoleEntity record);
    /**
     * <p>@Description 根据主键查询用户角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:42 </p>
     * @param surId 用户角色ID
     * @return com.www.myblog.admin.data.entity.SysUserRoleEntity 用户角色信息
     */
    SysUserRoleEntity selectByPrimaryKey(Long surId);
    /**
     * <p>@Description 根据主键更新用户角色信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:42 </p>
     * @param record 角色用户信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(SysUserRoleEntity record);
}