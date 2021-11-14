package com.www.myblog.admin.config.security;

import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

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
    private ISysUserService sysUserService;
    /**
     * <p>@Description 加载用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:12 </p>
     * @param userId 用户ID
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LOG.info("-----> security加载{}用户信息",userId);
        SysUserEntity sysUserEntity = sysUserService.findUserById(userId);
        if (sysUserEntity == null) {
            return null;
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //密码必须加密，否则无效
        User user = new User(sysUserEntity.getUserId(), new BCryptPasswordEncoder().encode(sysUserEntity.getPassWord()),authorities);
        //获取当前session
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("sysUser",sysUserEntity);
        return user;
    }
}
