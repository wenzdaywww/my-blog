package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 用户角色信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
@TableName("SYS_USER_ROLE")
public class SysUserRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 用户角色主键
    */
    @TableId("SUR_ID")
    private Long surId;
    /**
    * 用户ID
    */
    @TableField("USER_ID")
    private String userId;
    /**
    * 角色ID
    */
    @TableField("ROLE_ID")
    private Long roleId;
    /**
    * 更新时间
    */
    @TableField("SYS_UPDATE_TIME")
    private Date sysUpdateTime;
    /**
    * 创建时间
    */
    @TableField("SYS_CREATE_TIME")
    private Date sysCreateTime;
}