package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 角色菜单信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
@TableName("SYS_ROLE_MENU")
public class SysRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 角色菜单主键
    */
    @TableId("SRM_ID")
    private Long srmId;
    /**
    * 角色ID
    */
    @TableField("role_Id")
    private Long roleId;
    /**
    * 菜单ID
    */
    @TableField("MENU_ID")
    private Long menuId;
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