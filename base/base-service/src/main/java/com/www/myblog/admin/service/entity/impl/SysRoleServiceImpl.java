package com.www.myblog.admin.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.mapper.SysRoleMapper;
import com.www.myblog.admin.service.entity.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description SYS_ROLE表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleCode 角色名称
     * @return  角色信息
     */
    @Override
    public List<SysRoleEntity> findRoleEntityByName(String... roleCode) {
        if(roleCode == null){
            return null;
        }
        QueryWrapper<SysRoleEntity> roleWrapper = new QueryWrapper<>();
        roleWrapper.lambda().in(SysRoleEntity::getRoleCode,roleCode);
        return sysRoleMapper.selectList(roleWrapper);
    }

    /**
     * <p>@Description 根据角色名称查询角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 22:13 </p>
     * @param roleCode 角色名称
     * @return com.www.myblog.admin.data.entity.SysUserEntity 角色信息
     */
    @Override
    public SysRoleEntity findRoleEntityByName(String roleCode) {
        if(StringUtils.isBlank(roleCode)){
            return null;
        }
        QueryWrapper<SysRoleEntity> roleWrapper = new QueryWrapper<>();
        roleWrapper.lambda().eq(SysRoleEntity::getRoleCode,roleCode);
        return sysRoleMapper.selectOne(roleWrapper);
    }
}
