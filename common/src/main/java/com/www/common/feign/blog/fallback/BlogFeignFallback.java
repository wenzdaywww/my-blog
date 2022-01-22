package com.www.common.feign.blog.fallback;

import com.www.common.feign.blog.IBlogFeignService;
import com.www.common.pojo.dto.response.ResponseDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>@Description blog应用的服务降级处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/21 19:47 </p>
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "com.www.common.feign",name = {"blog"})
public class BlogFeignFallback implements FallbackFactory<IBlogFeignService> {
    /**
     * <p>@Description 服务降级处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:53 </p>
     * @param throwable
     * @return com.www.common.feign.IBlogFeignService
     */
    @Override
    public IBlogFeignService create(Throwable throwable) {
        log.error("blog服务降级,失败原因:",throwable);
        return new IBlogFeignService() {
            /**
             * <p>@Description 查询当前登录用户的博客数量 </p>
             * <p>@Author www </p>
             * <p>@Date 2022/1/20 21:16 </p>
             * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
             */
            @Override
            public ResponseDTO<Integer> findUserBlogNum() {
                log.error("blog服务降级：查询用户的博客数量");
                return null;
            }
        };
    }
}
