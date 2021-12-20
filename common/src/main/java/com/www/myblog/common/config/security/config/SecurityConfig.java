package com.www.myblog.common.config.security.config;

import com.www.myblog.common.config.security.filter.JwtAuthorizationTokenFilter;
import com.www.myblog.common.config.security.filter.SecurityAccessDecisionManager;
import com.www.myblog.common.config.security.filter.SecurityMetadataSource;
import com.www.myblog.common.config.security.handler.*;
import com.www.myblog.common.config.security.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>@Description Security配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:10 </p>
 */
@Slf4j
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationEntryHandler authenticationEntryHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private SessionExpiredHandler sessionExpiredHandler;
    @Autowired
    private URLAccessDeniedHandler urlAccessDeniedHandler;
    @Autowired
    private SecurityMetadataSource securityMetadataSource;
    @Autowired
    private SecurityAccessDecisionManager securityAccessDecisionManager;
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
        log.info("=====> 配置访问的安全拦截策略");
        //关闭CSRF（防止网站攻击）
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
            .maximumSessions(1)//单点登录
            .expiredSessionStrategy(sessionExpiredHandler);//会话过期处理
        http.authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setAccessDecisionManager(securityAccessDecisionManager);//访问权限角色验证
                o.setSecurityMetadataSource(securityMetadataSource);//访问权限角色配置
                return o;
            }
        });
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryHandler);//匿名用户访问无权限资源时的异常处理
        http.exceptionHandling().accessDeniedHandler(urlAccessDeniedHandler);//登录的用户访问无权限资源时的异常处理
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
        log.info("=====> 配置认证用户信息");
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
