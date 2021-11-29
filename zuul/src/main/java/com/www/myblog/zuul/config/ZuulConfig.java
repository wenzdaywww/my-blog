package com.www.myblog.zuul.config;

import com.www.myblog.zuul.config.filter.PostFilter;
import com.www.myblog.zuul.config.filter.PreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@Description zuul配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/29 21:33 </p>
 */
@Configuration
public class ZuulConfig {
    /**
     * <p>@Description 添加pre过滤器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:40 </p>
     * @return com.www.myblog.zuul.config.PreFilter
     */
    @Bean
    public PreFilter preFilter(){
        return new PreFilter();
    }
    /**
     * <p>@Description 添加post过滤器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:40 </p>
     * @return com.www.myblog.zuul.config.PostFilter
     */
    @Bean
    public PostFilter postFilter(){
        return new PostFilter();
    }
}
