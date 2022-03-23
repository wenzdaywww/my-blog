package com.www.myblog.blog;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.www.common.config.feign.MyFeignAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>@Description blog启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:56 </p>
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, MybatisPlusAutoConfiguration.class})
@EnableEurekaClient
@EnableCircuitBreaker //开启hystrix熔断器
@MapperScan(basePackages = {"com.www.myblog.blog.data"})
@EnableFeignClients(basePackages = {"com.www.myblog.common.config.feign"})//配置feign扫描路径
@RibbonClient(name = "${com.www.myblog.feign.base}",configuration = MyFeignAutoConfiguration.class)//配置base服务的负载均衡策略
public class BlogApplication {
    public static void main(String[] args) {
        //配置application加密的密钥
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(BlogApplication.class, args);
    }
}
