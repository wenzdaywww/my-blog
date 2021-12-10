package com.www.myblog.admin.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>@Description 菜单表DTO </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:21 </p>
 */
@Data
public class SysMenuDTO implements Serializable {
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
    /**  子菜单 **/
    List<SysMenuDTO> subMenu;

    private static final long serialVersionUID = 1L;
}