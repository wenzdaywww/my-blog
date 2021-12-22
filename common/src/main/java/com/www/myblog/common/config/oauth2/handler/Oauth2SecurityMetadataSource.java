package com.www.myblog.common.config.oauth2.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/22 22:28 </p>
 */
@Slf4j
@Component
public class Oauth2SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    /**  **/
    public static String AT = "@";
    /** 路径匹配器 **/
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /** 配置属性的前缀: 客户端访问范围 */
    public static String CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE = "client-access-scope" + AT;
    /** 配置属性的前缀: 客户端权限 */
    public static String CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY = "client-authority" + AT;
    /** 配置属性的前缀: 用户权限 */
    public static String CONFIG_ATTR_PREFIX_USER_AUTHORITY = "user-authority" + AT;
    /** 资源服务 ID */
    @Value("${spring.application.name}")
    private String resourceId;
    /**
     * <p>@Description 加载权限数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/22 23:22 </p>
     * @param object
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("=====> 加载权限数据");
        String requestUrl = ((FilterInvocation) object).getRequestUrl();//请求路径
        //通过要访问的端点和当前资源服务器 ID 获取可访问当前资源的【客户端访问范围】,【客户端权限】和【用户权限】集合,
        //约定, 每一种权限按照约定的前缀放入集合, 便于 Oauth2AccessDecisionManager获取.
        //然后, Oauth2AccessDecisionManager获取 根据 OAuth2Authentication 判断 authorities / scopes 是否在集合中
        //加载客户端访问范围
        Map<Object, Object> clientAccessScopeResourceAddressMapping = new HashMap<>();
        clientAccessScopeResourceAddressMapping.put("all","/user/**");
        Collection<ConfigAttribute> configAttributes = clientAccessScopeResourceAddressMapping.keySet()
                .stream()
                .filter(clientAccessScopeName ->
                        antPathMatcher.match(MapUtils.getString(clientAccessScopeResourceAddressMapping, clientAccessScopeName), requestUrl)
                )
                .map(clientAccessScopeName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE, clientAccessScopeName))).collect(Collectors.toSet());

        //加载客户端权限
        Map<Object, Object> clientAuthorityResourceAddressMapping = new HashMap<>();
        clientAuthorityResourceAddressMapping.put("admin","/user/**");
        clientAuthorityResourceAddressMapping.put("user","/user/**");
        configAttributes.addAll(clientAuthorityResourceAddressMapping.keySet()
                .stream()
                .filter(clientAuthorityName ->
                        antPathMatcher.match(MapUtils.getString(clientAuthorityResourceAddressMapping, clientAuthorityName), requestUrl)
                )
                .map(clientAuthorityName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY, clientAuthorityName)))
                .collect(Collectors.toSet())
        );
        //加载用户权限
        Map<Object, Object> userAuthorityResourceAddressMapping = new HashMap<>();
        userAuthorityResourceAddressMapping.put("user","/user/**");
        configAttributes.addAll(userAuthorityResourceAddressMapping.keySet()
                .stream()
                .filter(userAuthorityName ->
                        antPathMatcher.match(MapUtils.getString(userAuthorityResourceAddressMapping, userAuthorityName), requestUrl)
                )
                .map(userAuthorityName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_USER_AUTHORITY, userAuthorityName)))
                .collect(Collectors.toSet())
        );
        // ~ 为 AccessDecisionManager 提供包含匹配当前访问的资源端点的 ClientAuthority, UserAuthority, 以及 ClientAccessScope 的集合
        //   格式:
        //       - ClientAccessScope: ClientAccessScope.CACHE_PREFIX@ClientAccessScopeName
        //       - ClientAuthority: ClientAuthority.CACHE_PREFIX@ClientAuthorityName
        //       - UserAuthority: UserAuthority.CACHE_PREFIX@UserAuthorityName
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        log.debug("=====> 获取所有配置属性");
        throw new UnsupportedOperationException("不支持的操作!");
    }
    @Override
    public boolean supports(Class<?> clazz) {
        log.debug("=====> CustomFilterInvocationSecurityMetadataSource :: supports :: {}", clazz.getCanonicalName());
        // ~ FilterInvocation: 持有与 HTTP 过滤器相关的对象
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}