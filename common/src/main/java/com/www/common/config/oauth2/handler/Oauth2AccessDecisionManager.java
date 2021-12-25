package com.www.common.config.oauth2.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>@Description 访问决策管理器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 19:22 </p>
 */
@Slf4j
@Component
public class Oauth2AccessDecisionManager implements AccessDecisionManager {
    /**
     * <p>@Description url访问验证 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 22:01 </p>
     * @param authentication
     * @param o
     * @param collection
     * @return void
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("=====> 2、当前URL访问scope范围验证");
        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Iterator<ConfigAttribute> iterator = collection.iterator();
            while (iterator.hasNext()) {
                ConfigAttribute ca = iterator.next();
                String needScope = ca.getAttribute();//当前请求需要的scope权限
                //当前角色拥有scope范围
                Collection<String> scopeList = oAuth2Authentication.getOAuth2Request().getScope();
                if(CollectionUtils.isEmpty(scopeList)){
                    log.info("=====> 2.1、当前URL访问scope范围验证-无访问范围权限");
                    throw new AccessDeniedException("无访问范围权限");
                }
                for (String scope : scopeList){
                    if(StringUtils.equals(needScope,scope)){
                        log.info("=====> 2.2、当前URL访问scope范围验证-访问范围权限验证通过");
                        return;
                    }
                }
            }
            //用户拥有的角色统一在方法上使用@PreAuthorize注解校验
            log.info("=====> 2.1、当前URL访问scope范围验证-无访问范围权限");
            throw new AccessDeniedException("无访问范围权限");
        }
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