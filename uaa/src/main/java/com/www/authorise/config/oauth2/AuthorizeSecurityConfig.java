package com.www.authorise.config.oauth2;

import com.www.authorise.config.handler.Oauth2LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    @Autowired
    private Oauth2LogoutSuccessHandler oauth2LogoutSuccessHandler;
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
        http.formLogin()
                .loginPage("/uaa-login") //自定义的登录页面 **重要**
                .loginProcessingUrl("/login")  //原始的处理登录的URL,保持和uaa_login.html的form表单的action一致 ，不要修改
                .permitAll() //放开
                .and()
                .authorizeRequests().anyRequest().authenticated()//其他请求必须登录
                .and()
                .csrf().disable();//关闭csrf
        http.logout()//退出
                .logoutUrl("/logout")//退出路径
                .logoutSuccessHandler(oauth2LogoutSuccessHandler)//退出成功处理逻辑
                .deleteCookies("JSESSIONID");//登出之后删除cookie
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态资源的拦截
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }
}
