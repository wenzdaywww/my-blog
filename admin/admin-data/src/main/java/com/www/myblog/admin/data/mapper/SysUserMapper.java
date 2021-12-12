package com.www.myblog.admin.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.myblog.admin.data.dto.SysUserDTO;
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
     * <p>@Description 根据ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 13:09 </p>
     * @param userId 用户id
     * @return com.www.myblog.admin.data.dto.SysUserDTO
     */
    SysUserDTO findUserInfoById(@Param("userId") String userId);
    /**
     * <p>@Description 查询所有用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 17:42 </p>
     * @param page 分页信息
     * @param userDTO 查询条件
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.www.myblog.admin.data.dto.SysUserDTO>
     */
    Page<SysUserDTO> findAllUser(Page<SysUserDTO> page,@Param("obj") SysUserDTO userDTO);
    /**
     * <p>@Description 查询用户拥有的角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:45 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.admin.data.entity.SysRoleEntity> 角色信息
     */
    List<SysRoleEntity> findUserRole(@Param("userId") String userId);
}