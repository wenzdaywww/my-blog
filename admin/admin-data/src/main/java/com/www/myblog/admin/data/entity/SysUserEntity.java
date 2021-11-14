package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 用户信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
@TableName("SYS_USER")
public class SysUserEntity implements Serializable {
    /**
    * 用户主键
    */
    private Long suId;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 用户昵称
    */
    private String userName;

    /**
    * 密码
    */
    private String passWord;

    /**
    * 手机号
    */
    private String phoneNum;

    /**
    * 生日
    */
    private Date birthday;

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
    * 更新时间
    */
    private Date sysUpdateTime;

    /**
    * 创建时间
    */
    private Date sysCreateTime;

    private static final long serialVersionUID = 1L;
}