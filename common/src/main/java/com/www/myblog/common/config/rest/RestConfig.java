package com.www.myblog.common.config.rest;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>@Description Restful API调用配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:20 </p>
 */
@Configuration
public class RestConfig {
    private static Logger logger = LoggerFactory.getLogger(RestConfig.class);
    /**
     * <p>@Description 注入RestTemplate </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:20 </p>
     * @return org.springframework.web.client.RestTemplate
     */
    @Bean
    @LoadBalanced //此注解开启负载均衡
    public RestTemplate getRestTemplate(){
        logger.info("=====> 加载RestTemplate");
        return new RestTemplate();
    }
    /**
     * <p>@Description 添加全局负载均衡策略 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/7 15:04 </p>
     * @return com.netflix.loadbalancer.IRule
     */
    @Bean
    public IRule getRule(){
        logger.info("=====> 加载全局负载均衡策略");
        return new RandomRule();
    }
}
