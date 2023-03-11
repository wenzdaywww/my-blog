package com.www.myblog.base.controller.user;

import com.www.common.data.dto.response.ResponseDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.service.user.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description 用户注册contoller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/19 22:14 </p>
 */
@RestController
@RequestMapping("new")
public class RegistController {
    @Autowired
    private IUserInfoService userInfoService;
    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:20 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("user")
    public ResponseDTO<String> createUser(SysUserDTO user){
        return userInfoService.createUser(user);
    }
}
