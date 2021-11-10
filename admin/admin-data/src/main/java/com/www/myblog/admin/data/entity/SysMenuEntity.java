package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>@Description 菜单表 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:21 </p>
 */
@Data
public class SysMenuEntity implements Serializable {
    /**
    * 菜单主键
    */
    private Long menuId;

    /**
    * 菜单编码
    */
    private String menuCode;

    /**
    * 菜单名称
    */
    private String menuName;

    /**
    * 父级菜单ID
    */
    private Long parentId;

    /**
    * 菜单图标
    */
    private String menuIcon;

    /**
    * 菜单路径
    */
    private String menuUrl;

    /**
    * 菜单序号
    */
    private Integer menuOrder;

    /**
    * 是否删除：1删除，0未删除
    */
    private String isDelete;

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