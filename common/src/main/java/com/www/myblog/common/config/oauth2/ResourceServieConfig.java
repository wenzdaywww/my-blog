package com.www.myblog.common.config.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * <p>@Description 资源服务方的认证配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 21:24 </p>
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServieConfig extends ResourceServerConfigurerAdapter {
    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    @Qualifier("anonymousAccessHandler")
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    @Qualifier("unauthorizedAccessHandler")
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    @Qualifier("oauth2SecurityMetadataSource")
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Autowired
    @Qualifier("oauth2AccessDecisionManager")
    private AccessDecisionManager accessDecisionManager;

    /**
     * <p>@Description 配置资源服务方验证方式 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 16:25 </p>
     * @param resources
     * @return void
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        log.info("=====> 配置资源服务方验证方式");
        resources.resourceId(resourceId) //资源ID
                // .tokenServices(tokenServices()) //远程校验token时需要
                .tokenStore(tokenStore) //jwt校验token
                .stateless(true);
        resources.authenticationEntryPoint(authenticationEntryPoint);//匿名用户访问无权限资源时的异常处理
        resources.accessDeniedHandler(accessDeniedHandler);//登录的用户访问无权限资源时的异常处理
    }
    /**
     * <p>@Description 配置用户的安全拦截策略 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 13:04 </p>
     * @param http
     * @return void
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("=====> 配置资源服务方的安全拦截策略");
//        http.authorizeRequests()
//            .antMatchers("/**").access("#oauth2.hasScope('all')");
        http.authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//访问权限角色验证
                        o.setSecurityMetadataSource(securityMetadataSource);//访问权限角色配置
                        return o;
                    }
                });
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//关闭session策略
    }
    /**
     * <p>@Description 远程校验token </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 12:28 </p>
     * @return org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
     */
    @Deprecated
    public ResourceServerTokenServices tokenServices(){
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:8003/oauth/check_token");
        services.setClientId("blog");
        services.setClientSecret("wenzday");
        return services;
    }
}
