package com.www.myblog.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>@Description eureka注册中心服务启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 15:23 </p>
 */
@SpringBootApplication
@EnableEurekaServer //开启eureka注册中心服务
public class EurekaApplication {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(EurekaApplication.class,args);
    }
}
