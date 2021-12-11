package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 角色信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
@TableName("SYS_ROLE")
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 角色主键
    */
    @TableId("ROLE_ID")
    private Long roleId;
    /**
    * 角色昵称
    */
    @TableField("ROLE_NAME")
    private String roleName;
    /**
    * 角色描述
    */
    @TableField("DESCRIPTION")
    private String description;
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