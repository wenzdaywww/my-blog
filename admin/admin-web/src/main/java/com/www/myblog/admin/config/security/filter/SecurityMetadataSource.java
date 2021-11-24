package com.www.myblog.admin.config.security.filter;

import com.www.myblog.admin.data.dto.SysRoleMenuDTO;
import com.www.myblog.admin.service.ISysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>@Description 安全元数据源配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 18:22 </p>
 */
@Component
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Logger LOG = LoggerFactory.getLogger(SecurityMetadataSource.class);
    @Autowired
    private ISysMenuService sysMenuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * <p>@Description 访问权限角色配置 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:38 </p>
     * @param o
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        LOG.info("-----> 3、访问权限角色配置");
        String requestURL = ((FilterInvocation)o).getRequestUrl();
        List<SysRoleMenuDTO> roleMenuList = sysMenuService.findAllSecurityMenu();
        if(CollectionUtils.isNotEmpty(roleMenuList)){
            List<String> roleList = new ArrayList<>();
            for (SysRoleMenuDTO dto : roleMenuList){
                if(antPathMatcher.match(dto.getMenuUrl(),requestURL)){
                    roleList.add(dto.getRoleName());
                }
            }
            String[] roleArr = roleList.toArray(new String[roleList.size()]);
            return SecurityConfig.createList(roleArr);
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
