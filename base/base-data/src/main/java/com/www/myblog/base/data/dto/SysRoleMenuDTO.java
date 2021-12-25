package com.www.myblog.base.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 角色菜单信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
public class SysRoleMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
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
     * 菜单类型：1页面菜单，2权限菜单
     */
    private String menuType;
    /**
     * 是否删除：1删除，0未删除
     */
    private String isDelete;
    /**
    * 角色菜单主键
    */
    private Long srmId;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 创建时间
    */
    private Date createTime;
}