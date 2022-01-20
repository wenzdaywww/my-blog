package com.www.common.feign;

import com.www.common.pojo.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>@Description blog应用提供的对外服务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:29 </p>
 */
@Component
@FeignClient(value = "my-blog")//服务提供者名称
@ConditionalOnClass(FeignClient.class)
public interface IBlogFeignService {
    /**
     * <p>@Description 查询用户的博客数量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/20 21:16 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    @GetMapping("/feign/blogs")
    ResponseDTO<Integer> findUserBlogNum(@RequestParam("userId") String userId);
}
