package com.www.myblog.admin.config.security.impl;

import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description 用户详细信息服务类，用于实现spring security的登录认证 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:12 </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private IUserService userService;
    /**
     * <p>@Description 加载用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:12 </p>
     * @param userId 用户ID
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LOG.info("-----> 2、登录加载{}用户信息",userId);
        SysUserEntity sysUserEntity = userService.findUserById(userId);
        if (sysUserEntity == null) {
            return null;
        }
        //查询用户的角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRoleEntity> roleList = userService.findUserRole(userId);
        if(CollectionUtils.isNotEmpty(roleList)){
            for (SysRoleEntity entity : roleList){
                authorities.add(new SimpleGrantedAuthority(entity.getRoleName()));
            }
        }
        //密码必须加密，否则无效
        User user = new User(sysUserEntity.getUserId(), sysUserEntity.getPassWord(), StringUtils.equals(sysUserEntity.getStateCd(),"1"),
                StringUtils.equals(sysUserEntity.getNotExpired(),"1"),StringUtils.equals(sysUserEntity.getCredentialsNotExpired(),"1"),
                StringUtils.equals(sysUserEntity.getNotLocked(),"1"),authorities);
        return user;
    }
}
