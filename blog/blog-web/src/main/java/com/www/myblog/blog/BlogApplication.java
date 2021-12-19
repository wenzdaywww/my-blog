package com.www.myblog.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>@Description blog启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:56 </p>
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.www.myblog.common","com.www.myblog.blog"}) //添加扫描包的路径
public class BlogApplication {
    public static void main(String[] args) {
        //配置application加密的密钥
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(BlogApplication.class, args);
    }
}
