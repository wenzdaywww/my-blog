package com.www.myblog.admin.data.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 用户信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
public class SysUserDTO implements Serializable {
    /**
    * 用户主键
    */
    private Long suId;

    /**
    * 用户ID
    */
    private String userId;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 角色昵称
     */
    private String roleName;
    /**
    * 用户昵称
    */
    private String userName;

    /**
    * 手机号
    */
    private String phoneNum;

    /**
    * 生日
    */
    private String birthday;

    /**
    * 性别：1男0女
    */
    private String sex;

    /**
    * 头像
    */
    private String photo;

    /**
    * 邮箱
    */
    private String eMail;

    /**
    * 用户状态：1有效，2注销，3封号
    */
    private String stateCd;
    /**
    * 是否未过期。默认为1未过期，0过期
    */
    private String notExpired;
    /**
    * 账号是否未锁定。默认为1未锁定，0锁定
    */
    private String notLocked;
    /**
    * 证书（密码）是否未过期。默认为1未过期，0过期
    */
    private String credentialsNotExpired;

    /**
    * 更新时间
    */
    private Date sysUpdateTime;

    /**
    * 创建时间
    */
    private Date sysCreateTime;

    private static final long serialVersionUID = 1L;
}