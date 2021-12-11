package com.www.myblog.admin.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>@Description 菜单表DTO </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:21 </p>
 */
@Data
public class SysMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 菜单主键
    */
    private Long menuId;
    /**
     * 菜单类型：1页面菜单，2权限菜单
     */
    private String menuType;
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
     * 菜单角色拥有
     */
    private String roleName;
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
    /**
     *  子菜单
     */
    List<SysMenuDTO> subMenu;
}