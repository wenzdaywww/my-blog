package com.www.myblog.admin.service;

import com.www.myblog.admin.data.dto.SysRoleDTO;
import com.www.myblog.admin.data.dto.SysUserDTO;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.common.pojo.ResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>@Description 用户信息service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:31 </p>
 */
public interface IUserService {
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserRoleDTO>>
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserDTO>>
     */
    ResponseDTO<List<SysUserEntity>> findAllUser(String stateCd, String userId,String userName, int pageNum, int pageSize);
    /**
     * <p>@Description 查询用户拥有的角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:45 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.admin.data.entity.SysRoleEntity> 角色信息
     */
    List<SysRoleEntity> findUserRole(String userId);
    /**
     * <p>@Description 根据用户ID查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 15:32 </p>
     * @param userId 用户ID
     * @return com.www.myblog.admin.data.entity.SysUserEntity 用户信息
     */
    SysUserEntity findUserById(String userId);
}
