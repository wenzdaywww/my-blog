package com.www.myblog.base.controller.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.www.common.data.response.Result;
import com.www.myblog.base.service.user.IUserInfoService;
import com.www.myblog.common.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
     * <p>@Description 查询多个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return Response<UserInfoDTO>
     */
    @GetMapping("users")
    @HystrixCommand(fallbackMethod = "findUserInfoListFallback")//设置备选方案
    public Result<List<UserInfoDTO>> findUserInfoList(@RequestParam("list") List<String> userList){
        return userInfoService.findUserInfoList(userList);
    }
    /**
     * <p>@Description 查询多个用户信息-服务熔断处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:57 </p>
     * @param userList 用户id集合
     * @return Response<UserInfoDTO>
     */
    public Result<List<UserInfoDTO>> findUserInfoListFallback(List<String> userList, Throwable throwable){
        log.error("服务熔断处理: 查询多个用户信息,异常信息:",throwable);
        List<UserInfoDTO> list = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(userList)){
            for (String userId : userList){
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setUserId(userId);
                list.add(userInfoDTO);
            }
        }
        return new Result<>(list);
    }
    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return Response<UserInfoDTO>
     */
    @GetMapping("user/{id}")
    @HystrixCommand(fallbackMethod = "findUserInfoFallback")//设置备选方案
    public Result<UserInfoDTO> findUserInfo(@PathVariable("id") String userId){
        return userInfoService.findUserInfo(userId);
    }
    /**
     * <p>@Description 查询用户信息-服务熔断处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:57 </p>
     * @param userId 用户id
     * @return Response<UserInfoDTO>
     */
    public Result<UserInfoDTO> findUserInfoFallback(String userId,Throwable throwable){
        log.error("服务熔断处理: 查询用户信息,异常信息:",throwable);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userId);
        return new Result<>(userInfoDTO);
    }
}
