package com.www.myblog.base.controller.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.base.service.user.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description base应用对外服务controller,匿名访问 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:22 </p>
 */
@Slf4j
@RestController
@RequestMapping("feign/anonymous")
public class BaseFeignAnonymousController {
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @GetMapping("user/{id}")
    @HystrixCommand(fallbackMethod = "findUserInfoFallback")//设置备选方案
    public ResponseDTO<UserInfoDTO> findUserInfo(@PathVariable("id") String userId){
        return userInfoService.findUserInfo(userId);
    }
    /**
     * <p>@Description 查询用户信息-服务熔断处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:57 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    public ResponseDTO<UserInfoDTO> findUserInfoFallback(String userId,Throwable throwable){
        log.error("服务熔断处理: 查询用户信息,异常信息:",throwable);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userId);
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,userInfoDTO);
    }
}
