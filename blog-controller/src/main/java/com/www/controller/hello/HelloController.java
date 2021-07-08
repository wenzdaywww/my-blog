package com.www.controller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author www
 * @version 1.0
 * @description 测试首页
 * @date 2021/7/9 00:07
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public @ResponseBody Object hello(){
        return "Welcome to my blog！";
    }
}
