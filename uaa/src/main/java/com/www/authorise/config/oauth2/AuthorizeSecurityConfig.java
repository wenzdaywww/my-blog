package com.www.authorise.config.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>@Description 认证授权服务提供方的Security配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:10 </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //配置基于方法的安全认证,必要
public class AuthorizeSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * <p>@Description 配置密码加密方式 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:51 </p>
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * <p>@Description 配置认证管理器，使用密码模式必要 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:54 </p>
     * @return org.springframework.security.authentication.AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
    /**
     * <p>@Description 配置用户的安全拦截策略 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 13:04 </p>
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//关闭csrf跨域检查
        http.authorizeRequests().anyRequest().authenticated();//其他请求需要登录
        http.formLogin();//跳转默认登录页面
    }
}