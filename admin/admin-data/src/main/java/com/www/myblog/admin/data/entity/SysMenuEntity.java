package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 菜单表 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:21 </p>
 */
@Data
@TableName("SYS_MENU")
public class SysMenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 菜单主键
    */
    @TableId(value = "MENU_ID",type = IdType.AUTO)
    private Long menuId;
    /**
    * 菜单编码
    */
    @TableField("MENU_CODE")
    private String menuCode;
    /**
    * 菜单名称
    */
    @TableField("MENU_NAME")
    private String menuName;
    /**
    * 父级菜单ID
    */
    @TableField("PARENT_ID")
    private Long parentId;
    /**
    * 菜单图标
    */
    @TableField("MENU_ICON")
    private String menuIcon;
    /**
    * 菜单路径
    */
    @TableField("MENU_URL")
    private String menuUrl;
    /**
    * 菜单序号
    */
    @TableField("MENU_ORDER")
    private Integer menuOrder;
    /**
    * 菜单类型：1页面菜单，2权限菜单
    */
    @TableField("MENU_TYPE")
    private String menuType;
    /**
    * 是否删除：1删除，0未删除
    */
    @TableField("IS_DELETE")
    private String isDelete;
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