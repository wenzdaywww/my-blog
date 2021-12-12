package com.www.myblog.admin.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.admin.data.entity.SysUserRoleEntity;
import com.www.myblog.admin.data.entity.UserFriendsEntity;
import com.www.myblog.admin.data.mapper.SysUserRoleMapper;
import com.www.myblog.admin.data.mapper.UserFriendsMapper;
import com.www.myblog.admin.service.entity.ISysRoleMenuService;
import com.www.myblog.admin.service.entity.ISysUserRoleService;
import com.www.myblog.admin.service.entity.IUserFriendsService;
import org.springframework.stereotype.Service;

/**
 * <p>@Description n SYS_USER_ROLE表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements ISysUserRoleService {
}
