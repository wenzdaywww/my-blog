package com.www.myblog.common.config.feign.blog;

import com.www.myblog.common.config.feign.blog.fallback.BlogFeignFallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <p>@Description blog应用提供的对外服务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:29 </p>
 */
@Component
@FeignClient(value = "${com.www.common.feign.blog}",fallbackFactory = BlogFeignFallback.class)//服务提供者名称
@ConditionalOnProperty(prefix = "com.www.common.feign",name = {"blog"})
public interface IBlogFeignService {
}
