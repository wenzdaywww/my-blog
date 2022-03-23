package com.www.myblog.common.config.feign.base;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.common.config.feign.base.fallback.BaseFeignFallback;
import com.www.myblog.common.dto.UserInfoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>@Description base应用提供的对外服务接口，匿名访问 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:29 </p>
 */
@Component
@FeignClient(value = "${com.www.myblog.feign.base}",fallbackFactory = BaseFeignFallback.class)//服务提供者名称
@ConditionalOnProperty(prefix = "com.www.myblog.feign",name = "base")
public interface IBaseFeignService {
    /**
     * <p>@Description 查询多个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @GetMapping("/feign/anonymous/users")
    ResponseDTO<List<UserInfoDTO>> findUserInfoList(@RequestParam("list") List<String> userList);
    /**
     * <p>@Description 校验用户是否存在 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean>
     */
    @PostMapping("/feign/oauth/exist")
    ResponseDTO<Boolean> validateUserExist(@RequestParam("list") List<String> userList);
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
