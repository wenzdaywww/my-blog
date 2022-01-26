package com.www.myblog.base.controller.user;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.constant.AuthorityContant;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.service.user.IUserInfoService;
import com.www.common.pojo.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>@Description 用户信息controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/30 20:54 </p>
 */
@RestController
@RequestMapping("user")
@PreAuthorize(AuthorityContant.ALL)
public class UserController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * <p>@Description 更新当前登录的用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("pwd")
    public ResponseDTO<String> updateUserPwd(SysUserDTO user){
        if(user != null){
            user.setUserId(jwtTokenConverter.getUserId());
        }
        return userInfoService.updateUserPwd(user);
    }
    /**
     * <p>@Description 查询当前登录的用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("router")
    public ResponseDTO<List<SysMenuDTO>> findUserRouter(){
        return userInfoService.findUserRouter(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询当前登录的用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("menu")
    public ResponseDTO<List<SysMenuDTO>> findUserMenu(){
        return userInfoService.findUserMenu(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 更新当前登录的用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo 头像文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("photo")
    public ResponseDTO<String> uploadPhoto(MultipartFile photo){
        return userInfoService.uploadPhoto(photo,jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 更新当前登录的用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("edit")
    public ResponseDTO<String> updateUserInfo(SysUserDTO user){
        if(user != null){
            user.setUserId(jwtTokenConverter.getUserId());
        }
        return userInfoService.updateUserInfo(user);
    }
    /**
     * <p>@Description 查询当前登录的用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.base.data.dto.SysUserDTO>
     */
    @GetMapping("info")
    public ResponseDTO<SysUserDTO> findUser(){
        return userInfoService.findUser(jwtTokenConverter.getUserId());
    }
}
