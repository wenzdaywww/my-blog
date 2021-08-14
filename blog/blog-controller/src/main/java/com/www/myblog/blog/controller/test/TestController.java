package com.www.myblog.blog.controller.test;

import com.www.myblog.common.api.MyBlogAdminService;
import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * <p>@Description 测试 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:38 </p>
 */
@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MyBlogAdminService myBlogAdminService;
    /**
     * <p>@Description 测试方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/14 16:17 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("/test/rest/{name}")
    public ResponseDTO test(@PathVariable("name") String name){
        return restTemplate.getForObject("http://my-blog-admin/admin/test/"+name,ResponseDTO.class);
    }
    /**
     * <p>@Description 测试方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/14 16:17 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("/test/feign/{name}")
    public ResponseDTO feign(@PathVariable("name") String name){
        return myBlogAdminService.test(name);
    }
}
