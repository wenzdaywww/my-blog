package com.www.myblog.admin.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.regexp.internal.RE;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.mapper.SysRoleMapper;
import com.www.myblog.admin.service.entity.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description SYS_ROLE表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleName 角色名称
     * @return com.www.myblog.admin.data.entity.SysUserEntity 角色信息
     */
    @Override
    public SysRoleEntity findRoleEntityByName(String roleName) {
        if(StringUtils.isBlank(roleName)){
            return null;
        }
        QueryWrapper<SysRoleEntity> roleWrapper = new QueryWrapper<>();
        roleWrapper.lambda().eq(SysRoleEntity::getRoleName,roleName);
        return sysRoleMapper.selectOne(roleWrapper);
    }
}
