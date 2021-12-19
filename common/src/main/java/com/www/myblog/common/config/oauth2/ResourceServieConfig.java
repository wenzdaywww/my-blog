package com.www.myblog.common.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 21:24 </p>
 */
@Configuration
@EnableResourceServer
public class ResourceServieConfig extends ResourceServerConfigurerAdapter {
    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private TokenStore tokenStore;
    /**
     * <p>@Description 配置资源验证方式 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 16:25 </p>
     * @param resources
     * @return void
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId) //资源ID
                // .tokenServices(tokenServices()) //远程校验token时需要
                .tokenStore(tokenStore) //jwt校验token
                .stateless(true);
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
        http.authorizeRequests()
            .antMatchers("/**").access("#oauth2.hasScope('all')")
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//关闭session策略
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
