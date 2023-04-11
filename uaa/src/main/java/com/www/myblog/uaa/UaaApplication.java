package com.www.myblog.uaa;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>@Description UAA统一认证服务启动类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 12:57 </p>
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, MybatisPlusAutoConfiguration.class})
@EnableEurekaClient
@EnableScheduling //开启定时任务
public class UaaApplication {
    /**
     * <p>@Description 启动方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 12:58 </p>
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        //配置application加密的密钥
        System.setProperty("jasypt.encryptor.password","wenzday");
        SpringApplication.run(UaaApplication.class, args);
    }
}
