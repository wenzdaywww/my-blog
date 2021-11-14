package com.www.myblog.admin.controller.login;

import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.service.login.ILoginService;
import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description 登入controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 14:30 </p>
 */
@RestController
public class LoginController {
    @Autowired
    private ILoginService loginService;
    /**
     * <p>@Description 用户登入 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 14:38 </p>
     * @param userId 用户ID
     * @param password 密码
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.admin.data.entity.SysUserEntity>
     */
    @GetMapping("/login/{id}/{pwd}")
    public ResponseDTO<SysUserEntity> login(@PathVariable("id") String userId, @PathVariable("pwd")String password){
        return loginService.userLogin(userId,password);
    }
}
