package com.www.common.config.druid.monitor;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>@Description druid监控平台配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 20:44 </p>
 */
@Slf4j
@Configuration
public class DruidMonitorConfig {
    /**
     * <p>@Description 设置druid后台监控功能
     *     因为spring boot内置了servlet容器，所以没有web.xml，替代方法使用：ServletRegistrationBean
     *     注：监控页面路径为：localhost:8080/druid </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:45 </p>
     * @return org.springframework.boot.web.servlet.ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParam = new HashMap<>();
        //登录的key固定，不能改变
        initParam.put("loginUsername","admin");
        initParam.put("loginPassword","www362412");
        //设置运行访问IP
        initParam.put("allow","");
        //设置禁止访问的IP，key值自定义
//        initParam.put("www","192.168.1.105");
        //设置初始化参数
        bean.setInitParameters(initParam);
        log.info("=====> 设置druid后台监控功能");
        return  bean;
    }
    /**
     * <p>@Description druid监控过滤器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:46 </p>
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParam = new HashMap<>();
        //设置不统计的过滤器
        initParam.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParam);
        log.info("=====> druid监控过滤器");
        return bean;
    }
}
