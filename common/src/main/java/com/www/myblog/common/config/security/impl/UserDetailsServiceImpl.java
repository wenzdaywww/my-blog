package com.www.myblog.common.config.security.impl;

import com.www.myblog.common.config.security.ISecurityServie;
import com.www.myblog.common.pojo.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description 用户详细信息服务类，用于实现spring security的登录认证 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:12 </p>
 */
@Slf4j
//@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private ISecurityServie securityUserServie;
    /**
     * <p>@Description 加载用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:12 </p>
     * @param userId 用户ID
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("=====> 2、登录加载{}用户信息",userId);
        UserDetailDTO userDTO = securityUserServie.findUserDetailById(userId);
        if (userDTO == null) {
            return null;
        }
        //查询用户的角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roleList = securityUserServie.findUserRole(userId);
        if(CollectionUtils.isNotEmpty(roleList)){
            for (String roleName : roleList){
                authorities.add(new SimpleGrantedAuthority(roleName));
            }
        }
        //密码必须加密，否则无效
        User user = new User(userDTO.getUserId(), userDTO.getPassWord(), userDTO.isEnabled(),
                userDTO.isAccountNonExpired(),userDTO.isCredentialsNonExpired(), userDTO.isAccountNonLocked(),authorities);
        return user;
    }
}
