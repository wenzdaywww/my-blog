package com.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author www
 * @version 1.0
 * @description pring boot启动类
 *  *  springboot项目代码必须放到SpringBootDemoApplication类同级目录或下级目录
 * @date 2021/7/7 23:44
 */
@SpringBootApplication
public class BlogApplication {
    /**
     * @author www
     * @date 2021/7/7 23:55
     * @description springboot启动方法
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
