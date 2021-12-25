package com.www.myblog.base.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>@Description 角色表Mapper </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 13:32 </p>
     * @return java.util.List<com.www.myblog.base.data.dto.SysUserRoleDTO>
     */
    List<SysRoleDTO> findAllRole();
}