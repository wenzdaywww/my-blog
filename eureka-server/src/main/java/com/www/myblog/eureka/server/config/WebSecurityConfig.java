package com.www.myblog.eureka.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>@Description eureka开启安全认证 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/4 19:32 </p>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * <p>@Description 授权认证 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/4 19:33 </p>
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 支持httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
