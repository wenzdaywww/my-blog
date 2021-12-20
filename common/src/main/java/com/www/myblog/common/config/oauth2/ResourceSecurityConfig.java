package com.www.myblog.common.config.oauth2;

import com.www.myblog.common.config.security.handler.AuthenticationEntryHandler;
import com.www.myblog.common.config.security.handler.URLAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <p>@Description 资源服务方的Security配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:10 </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //配置基于方法的安全认证,必要
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger LOG = LoggerFactory.getLogger(ResourceSecurityConfig.class);
    /**
     * <p>@Description 配置资源的安全拦截策略 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 13:04 </p>
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOG.info("=====> 配置资源的安全拦截策略");
        http.csrf().disable();//关闭csrf跨域检查
        http.authorizeRequests().antMatchers("/**").authenticated(); //必须认证通过的请求
        http.authorizeRequests().anyRequest().permitAll(); //其他请求
    }
}
