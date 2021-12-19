package com.www.authorise.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 22:55 </p>
 */
public class ClientExceptionFilter extends ClientCredentialsTokenEndpointFilter{
    private static Logger LOG = LoggerFactory.getLogger(ClientExceptionFilter.class);

    private AuthorizationServerSecurityConfigurer serverSecurityConfigurer;

    private AuthenticationEntryPoint entryPoint;

    public ClientExceptionFilter(AuthorizationServerSecurityConfigurer configurer){
        this.serverSecurityConfigurer = configurer;
    }
    /**
     * @param authenticationEntryPoint the authentication entry point to set
     */
    @Override
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        super.setAuthenticationEntryPoint(null);
        this.entryPoint = authenticationEntryPoint;
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationFailureHandler((request,response,e) -> {
            LOG.info("=====> 客户端失败");
            entryPoint.commence(request,response,e);
        });
        setAuthenticationSuccessHandler((request,response,e) -> {
            LOG.info("=====> 客户端成功");
        });
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return serverSecurityConfigurer.and().getSharedObject(AuthenticationManager.class);
    }
}
