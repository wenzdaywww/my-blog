package com.www.myblog.base.service.user;

import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.common.pojo.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>@Description 用户信息service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:31 </p>
 */
public interface IUserInfoService {
    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> updateUserPwd(SysUserDTO user);
    /**
     * <p>@Description 查询用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    ResponseDTO<List<SysMenuDTO>> findUserRouter(String userId);
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    ResponseDTO<List<SysMenuDTO>> findUserMenu(String userId);
    /**
     * <p>@Description 更新用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo 头像文件
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> uploadPhoto(MultipartFile photo, String userId);
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> updateUserInfo(SysUserDTO user);
    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.base.data.dto.SysUserDTO>
     */
    ResponseDTO<SysUserDTO> findUser(String userId);
    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:03 </p>
     * @param user
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> createUser(SysUserDTO user);
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserRoleDTO>>
     */
    ResponseDTO<List<SysRoleDTO>> findAllRole();
    /**
     * <p>@Description 更新用户状态 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 20:58 </p>
     * @param userId 用户id
     * @param stateCd 用户状态
     * @param notExpired 是否过期
     * @param notLocked 账号是否锁定
     * @param credentialsNotExpired 密码是否过期
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> updateState(String userId,String stateCd,String notExpired,String notLocked,String credentialsNotExpired);
    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum 当前页数
     * @param pageSize 页面条数
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserDTO>>
     */
    ResponseDTO<List<SysUserDTO>> findAllUser(String stateCd, String userId,String userName, int pageNum, int pageSize);
}
