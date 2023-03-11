package com.www.myblog.base.controller.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.www.common.config.oauth2.constant.AuthorityContant;
import com.www.common.data.dto.response.ResponseDTO;
import com.www.common.data.enums.ResponseEnum;
import com.www.myblog.base.service.user.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>@Description base应用对外服务controller,登录访问 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:22 </p>
 */
@Slf4j
@RestController
@RequestMapping("feign/oauth")
public class BaseFeignOauthController {
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * <p>@Description 校验用户是否存在 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.ResponseDTO<Boolean>
     */
    @PostMapping("exist")
    @PreAuthorize(AuthorityContant.ALL)
    @HystrixCommand(fallbackMethod = "validateUserExistFallback")//设置备选方案
    public ResponseDTO<Boolean> validateUserExist(@RequestParam("list") List<String> userList){
        return userInfoService.validateUserExist(userList);
    }
    /**
     * <p>@Description 校验用户是否存在-服务熔断处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:57 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.ResponseDTO<Boolean>
     */
    public ResponseDTO<Boolean> validateUserExistFallback(List<String> userList,Throwable throwable){
        log.error("服务熔断处理: 校验用户是否存在,异常信息:",throwable);
        return new ResponseDTO<>(ResponseEnum.SUCCESS,false);
    }
}
