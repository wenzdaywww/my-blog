package com.www.myblog.blog.controller.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.service.feign.IBlogArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description 博客文章对外服务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:13 </p>
 */
@Slf4j
@RestController
@RequestMapping("/feign")
public class BlogArticleController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IBlogArticleService blogArticleService;

    /**
     * <p>@Description 查询用户的博客数量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/20 21:16 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    @GetMapping("blogs")
    @HystrixCommand(fallbackMethod = "findUserBlogNumFallback")//设置备选方案
    public ResponseDTO<Integer> findUserBlogNum(){
        return blogArticleService.findUserBlogNum(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询用户的博客数量-服务熔断处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:57 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    public ResponseDTO<Integer> findUserBlogNumFallback(Throwable throwable){
        log.error("查询用户的博客数量-服务熔断处理,异常信息:",throwable);
        return null;
    }
}
