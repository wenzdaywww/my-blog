package com.www.myblog.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>@Description admin启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:23 </p>
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.www.myblog.common","com.www.myblog.admin"}) //添加扫描包的路径
public class AdminApplication {
    /**
     * <p>@Description 启动方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 17:09 </p>
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
