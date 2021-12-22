package com.www.myblog.common.config.oauth2.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/22 22:30 </p>
 */
@Slf4j
@Component
public class Oauth2AccessDecisionManager implements AccessDecisionManager {

    /**
     * 第一方客户端前端 CLIENT_AUTHORITY 名称
     */
    private static final String CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT = "FIRST_PARTY_FRONTEND_CLIENT";

    // =================================================================================================================

    private static final String CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY = Oauth2SecurityMetadataSource.CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY;

    private static final String CONFIG_ATTR_PREFIX_USER_AUTHORITY = Oauth2SecurityMetadataSource.CONFIG_ATTR_PREFIX_USER_AUTHORITY;

    private static final String CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE = Oauth2SecurityMetadataSource.CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE;

    // =================================================================================================================

    /**
     * 资源服务 ID
     */
    private String resourceId = "my-base";;

    /**
     * Description: 配置该资源服务的访问控制<br>
     * <dl>
     *     <dt>Access control principles:</dt>
     *     <dd>{@link OAuth2Authentication#isClientOnly()}{@code = true}: 验证客户端权限</dd>
     *     <dd>
     *         {@link OAuth2Authentication#isClientOnly()}{@code = false}: <br>
     *             - 如果是第一方客户端前端 ({@code CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT}), 校验用户权限;<br>
     *             - 如果是第三方应用, 校验用户全和客户端的权限;
     *     </dd>
     * </dl>
     *
     * @param authentication   {@link org.springframework.security.oauth2.provider.OAuth2Authentication}
     * @param configAttributes 由 {@link Oauth2SecurityMetadataSource} 组织的资源标识:
     *                         <pre>
     *                             - ClientAccessScope: ClientAccessScope.CACHE_PREFIX@ClientAccessScopeName<br>
     *                             - ClientAuthority: ClientAuthority.CACHE_PREFIX@ClientAuthorityName<br>
     *                             - UserAuthority: UserAuthority.CACHE_PREFIX@UserAuthorityName
     *                         </pre>
     * @see AccessDecisionManager#decide(Authentication, Object, Collection)
     * @see Oauth2SecurityMetadataSource#getAttributes(Object)
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug("=====> 配置该资源服务的访问控制");

        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        final FilterInvocation filterInvocation = (FilterInvocation) object;
        final String resourceAddress =
                StringUtils.join(filterInvocation.getRequestUrl(), Oauth2SecurityMetadataSource.AT, Objects.requireNonNull(resourceId, "资源服务器 ID 未定义!"));

        final boolean clientOnly = oAuth2Authentication.isClientOnly();
        final String principalName = oAuth2Authentication.getName();
        final Set<String> metadataSource = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toSet());

        if (clientOnly) {
            log.debug("Access controller :: 请求来自第一方客户端 ...");
            final Set<String> clientAuthorities = oAuth2Authentication.getOAuth2Request().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            if (metadataSource.stream()
                    .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY))
                    .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(clientAuthorities, StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY.length())))
            ) {
                throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (客户端: %s) 没有足够的权限访问该资源: %s", principalName, resourceAddress));
            }
        } else {
            log.debug("Access controller :: 请求可能来自第一方前端 ...");
            final Set<String> userAuthorities = oAuth2Authentication.getUserAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            // ~ 校验用户权限
            if (metadataSource.stream()
                    .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_USER_AUTHORITY))
                    .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(userAuthorities, StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_USER_AUTHORITY.length())))
            ) {
                throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (用户: %s) 没有足够的权限访问该资源: %s", principalName, resourceAddress));
            }

            if (!CollectionUtils.containsAny(userAuthorities, CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT)) {
                log.debug("Access controller :: 请求来自第三方客户端 ...");
                final Set<String> clientScopeNames = oAuth2Authentication.getOAuth2Request().getScope();
                // ~ 第三方应用: 还需要客户端 SCOPE
                if (metadataSource.stream()
                        .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE))
                        .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(clientScopeNames, StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE.length())))
                ) {
                    throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (客户端: %s) 的方位范围不包括资源: %s", principalName, resourceAddress));
                }
            }
        }
        log.debug("Access controller :: allowed");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        log.debug("CustomFilterInvocationSecurityMetadataSource :: supports :: {}", clazz.getCanonicalName());
        // ~ FilterInvocation: 持有与 HTTP 过滤器相关的对象
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}