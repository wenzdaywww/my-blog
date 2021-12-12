package com.www.myblog.common.api;

import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>@Description blog服务的feign客户端 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:51 </p>
 */
@Component
@FeignClient(value = "my-blog")
public interface MyBlogService {
    /**
     * <p>@Description 测试 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/14 16:53 </p>
     * @param name
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("/admin/test/{name}")
    ResponseDTO test(@PathVariable("name") String name);
}
