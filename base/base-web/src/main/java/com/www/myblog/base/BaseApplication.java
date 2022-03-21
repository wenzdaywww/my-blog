package com.www.myblog.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>@Description base启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:23 </p>
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //开启hystrix熔断器
@ComponentScan(basePackages = {"com.www.common","com.www.myblog.base"}) //配置要扫描的包路径
@MapperScan(basePackages = {"com.www.myblog.base.data"})
public class BaseApplication {
    /**
     * <p>@Description 启动方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 17:09 </p>
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        // 设置application.yml加密数据的密钥
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(BaseApplication.class, args);
    }
}
