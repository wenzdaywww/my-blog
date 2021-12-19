package com.www.myblog.blog.controller.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description 测试 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:38 </p>
 */
@RestController
@PreAuthorize("hasAnyAuthority('user')")
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "tets = userId";
    }

    @GetMapping("/find")
    public String find(){
        return "find = userId";
    }
}
