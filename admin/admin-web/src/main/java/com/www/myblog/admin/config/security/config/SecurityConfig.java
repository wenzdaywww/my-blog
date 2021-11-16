package com.www.myblog.admin.config.security.config;

import com.www.myblog.admin.config.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>@Description Security配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:10 </p>
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;
    /**
     * <p>@Description 授权,配置如何通过拦截器保护请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:10 </p>
     * @param http http请求
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭CSRF（防止网站攻击）
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//关闭session
        http.authorizeRequests().antMatchers("/test/**").permitAll()//设置允许访问的路径
            .anyRequest().hasAnyAuthority("admin");//设置运行角色的路径
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);//匿名用户访问无权限资源时的异常处理
        http.formLogin()//登入
            .loginProcessingUrl("/login")//登录表单form中action的地址，也就是处理认证请求的路径
            .usernameParameter("id")//登录表单form中用户名输入框input的name名，不修改的话默认是username
            .passwordParameter("pwd")//form中密码输入框input的name名，不修改的话默认是password
            .successHandler(loginSuccessHandler)//登录成功处理逻辑
            .failureHandler(loginFailureHandler);//登录失败处理逻辑
        http.logout()//退出
            .logoutUrl("/logout")//退出路径
            .logoutSuccessHandler(logoutSuccessHandler)//退出成功处理逻辑
            .deleteCookies("JSESSIONID");//登出之后删除cookie
        http.addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);//设置token解析过滤器在账号密码验证器之前
    }
    /**
     * <p>@Description 认证,配置user-detail（用户详细信息）服务 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:11 </p>
     * @param auth 认证信息
     * @return void
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //从数据库查询用户信息
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());;
    }
    /**
     * <p>@Description 配置Spring Security的Filter链 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:11 </p>
     * @param web
     * @return void
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态资源的拦截
    }
}
