package com.www.myblog.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * <p>@Description blog启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:56 </p>
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.www.myblog.common.api"})
@ComponentScan(basePackages = {"com.www.myblog.common","com.www.myblog.blog"})
public class BlogApplication {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(BlogApplication.class, args);
    }
}
