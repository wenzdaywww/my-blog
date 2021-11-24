package com.www.myblog.admin.config.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>@Description 访问决策管理器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 19:22 </p>
 */
@Component
public class SecurityAccessDecisionManager implements AccessDecisionManager {
    private static Logger LOG = LoggerFactory.getLogger(SecurityAccessDecisionManager.class);

    /**
     * <p>@Description url访问校验 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 22:01 </p>
     * @param authentication
     * @param o
     * @param collection
     * @return void
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        LOG.info("-----> 4、访问权限角色验证");
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            String needRole = ca.getAttribute();//当前请求需要的权限
            //当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    LOG.info("-----> 4.1、访问权限角色验证-权限通过");
                    return;
                }
            }
        }
        LOG.info("-----> 4.1、访问权限角色验证-无权限访问");
        throw new AccessDeniedException("无权限访问");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
