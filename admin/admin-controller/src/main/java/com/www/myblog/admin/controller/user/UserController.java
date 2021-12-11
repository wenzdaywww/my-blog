package com.www.myblog.admin.controller.user;

import com.www.myblog.admin.data.dto.SysMenuDTO;
import com.www.myblog.admin.data.dto.SysRoleDTO;
import com.www.myblog.admin.data.dto.SysUserDTO;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.service.user.IUserInfoService;
import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private IUserInfoService userInfoService;

    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("pwd")
    public ResponseDTO<String> updateUserPwd(SysUserDTO user){
        return userInfoService.updateUserPwd(user);
    }
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("menu")
    public ResponseDTO<List<SysMenuDTO>> findUserMenu(String userId){
        return userInfoService.findUserMenu(userId);
    }
    /**
     * <p>@Description 更新用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo 头像文件
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("photo")
    public ResponseDTO<String> uploadPhoto(MultipartFile photo,String userId){
        return userInfoService.uploadPhoto(photo,userId);
    }
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("edit")
    public ResponseDTO<String> updateUserInfo(SysUserDTO user){
        return userInfoService.updateUserInfo(user);
    }
    /**
     * <p>@Description 查询单个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.admin.data.dto.SysUserDTO>
     */
    @GetMapping("info")
    public ResponseDTO<SysUserDTO> findUser(String userId){
        return userInfoService.findUser(userId);
    }
    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:20 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping("new")
    public ResponseDTO<String> createUser(SysUserDTO user){
        return userInfoService.createUser(user);
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserDTO>>
     */
    @GetMapping("all")
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
    public ResponseDTO<String> updateState(String userId,String stateCd,String expired,String locked,String credentials){
        return userInfoService.updateState(userId,stateCd,expired,locked,credentials);
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserRoleDTO>>
     */
    @GetMapping("role")
    public ResponseDTO<List<SysRoleDTO>> findAllRole(){
        return userInfoService.findAllRole();
    }
}
