package com.www.myblog.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * <p>@Description blog启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:56 </p>
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //开启hystrix熔断器
@ComponentScan(basePackages = {"com.www.common","com.www.myblog.blog"}) //配置要扫描的包路径
@MapperScan(basePackages = {"com.www.myblog.blog.data"})
public class BlogApplication {
    public static void main(String[] args) {
        //配置application加密的密钥
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(BlogApplication.class, args);
    }
}
