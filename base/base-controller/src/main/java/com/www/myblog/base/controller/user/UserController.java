package com.www.myblog.base.controller.user;

import com.www.common.config.oauth2.token.JwtTokenConverter;
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
public class UserController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("pwd")
    @PreAuthorize("hasAnyAuthority('admin','user')")
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
    @PreAuthorize("hasAnyAuthority('admin','user')")
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
    @PreAuthorize("hasAnyAuthority('admin','user')")
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
    @PreAuthorize("hasAnyAuthority('admin','user')")
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
    @PreAuthorize("hasAnyAuthority('admin','user')")
    public ResponseDTO<String> updateUserInfo(SysUserDTO user){
        if(user != null){
            user.setUserId(jwtTokenConverter.getUserId());
        }
        return userInfoService.updateUserInfo(user);
    }
    /**
     * <p>@Description 查询单个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.base.data.dto.SysUserDTO>
     */
    @GetMapping("info")
    @PreAuthorize("hasAnyAuthority('admin','user')")
    public ResponseDTO<SysUserDTO> findUser(){
        return userInfoService.findUser(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询所有用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserDTO>>
     */
    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseDTO<List<SysUserDTO>> findAllUser(String stateCd, String userId, String userName, int pageNum, int pageSize){
        return userInfoService.findAllUser(stateCd,userId,userName,pageNum,pageSize);
    }
    /**
     * <p>@Description 更新用户状态 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 20:58 </p>
     * @param userId 用户id
     * @param stateCd 用户状态
     * @param expired 是否过期
     * @param locked 账号是否锁定
     * @param credentials 密码是否过期
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("state")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseDTO<String> updateState(String userId,String stateCd,String expired,String locked,String credentials){
        return userInfoService.updateState(userId,stateCd,expired,locked,credentials);
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserRoleDTO>>
     */
    @GetMapping("role")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseDTO<List<SysRoleDTO>> findAllRole(){
        return userInfoService.findAllRole();
    }
}
