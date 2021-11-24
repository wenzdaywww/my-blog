package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>@Description 用户信息Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    /**
     * <p>@Description 查询用户拥有的角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:45 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.admin.data.entity.SysRoleEntity> 角色信息
     */
    List<SysRoleEntity> findUserRole(@Param("userId") String userId);
    /**
     * <p>@Description 根据主键删除用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:37 </p>
     * @param suId 用户主键
     * @return int
     */
    int deleteByPrimaryKey(Long suId);
    /**
     * <p>@Description 插入数据，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:37 </p>
     * @param record 用户信息
     * @return int 插入条数
     */
    int insert(SysUserEntity record);
    /**
     * <p>@Description 插入数据，不包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:37 </p>
     * @param record 用户信息
     * @return int 插入条数
     */
    int insertNotNull(SysUserEntity record);
    /**
     * <p>@Description 根据主键查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:37 </p>
     * @param suId 用户主键
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    SysUserEntity selectByPrimaryKey(Long suId);
    /**
     * <p>@Description 根据用户主键更新用户信息，包括空字段 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/10 22:37 </p>
     * @param record 用户信息
     * @return int 更新条数
     */
    int updateByPrimaryKey(SysUserEntity record);
}