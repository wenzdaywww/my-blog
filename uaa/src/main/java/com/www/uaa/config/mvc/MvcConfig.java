package com.www.uaa.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>@Description 扩展MVC配置，用于自定义配置MVC </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:00 </p>
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /** 登录页面请求路径 **/
    public static final String LOGIN_PAGE = "/uaa-login";
    /**
     * <p>@Description 设置视图控制器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:00 </p>
     * @param registry 视图控制器
     * @return void
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("加载视图控制器");
        registry.addViewController("/uaa-login").setViewName("uaa_login");//自定义登录页面跳转
    }
}
