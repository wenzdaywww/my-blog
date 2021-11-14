package com.www.myblog.admin.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>@Description Security配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:10 </p>
 */
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
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
        http.authorizeRequests()
                //设置允许访问的路径
                .antMatchers("/admin/login","admin/outbound").permitAll()
                //设置运行角色的路径
//                .antMatchers("/admin/**").hasRole("admin")
                .and()
                //没有权限默认跳转登录页面
                .formLogin().loginPage("")//用户未登录时，访问任何资源都转跳到该路径，即登录页面
                .loginProcessingUrl("")//登录表单form中action的地址，也就是处理认证请求的路径
                .usernameParameter("id")//登录表单form中用户名输入框input的name名，不修改的话默认是username
                .passwordParameter("pwd")//form中密码输入框input的name名，不修改的话默认是password
                //登录认证成功后的处理器,如果设置了defaultSuccessUrl就不能使用该方法
                .successHandler(loginSuccessHandler)
                //登录认证失败后的处理器,如果设置了failureUrl就不能使用该方法
                .failureHandler(loginFailureHandler)
                .and()
                ///qadmin/logout非post请求，需要关闭CSRF
                .logout().logoutUrl("")//配置注销登录请求url为"/user/logout"，默认为"/logout"
                .logoutSuccessUrl("")
                .clearAuthentication(true)//清除身份认证信息，默认为true
                .invalidateHttpSession(true);//是否使session失效，默认为true
        //记住我功能
        http.rememberMe().rememberMeParameter("rmb");//配置记住我的参数
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
//        auth.userDetailsService(userDetailsService);
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
