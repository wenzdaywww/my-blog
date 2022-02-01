package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.SysUserRoleEntity;
import com.www.myblog.base.data.mapper.SysUserRoleMapper;
import com.www.myblog.base.service.entity.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description n SYS_USER_ROLE表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * <p>@Description 创建用户角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 12:39 </p>
     * @param entity 用户角色信息
     * @return boolean true创建成功，false创建失败
     */
    @Override
    public boolean createEntity(SysUserRoleEntity entity) {
        if(entity == null){
            return false;
        }
        int count = sysUserRoleMapper.insert(entity);
        return count > 0;
    }
}
