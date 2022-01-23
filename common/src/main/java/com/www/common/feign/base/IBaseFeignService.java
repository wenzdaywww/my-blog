package com.www.common.feign.base;

import com.www.common.feign.base.fallback.BaseFeignFallback;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>@Description base应用提供的对外服务接口，匿名访问 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:29 </p>
 */
@Component
@FeignClient(value = "${com.www.common.feign.base}",fallbackFactory = BaseFeignFallback.class)//服务提供者名称
@ConditionalOnProperty(prefix = "com.www.common.feign",name = {"base"})
public interface IBaseFeignService {
    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @GetMapping("/feign/anonymous/user/{id}")
    ResponseDTO<UserInfoDTO> findUserInfo(@PathVariable("id") String userId);
}
