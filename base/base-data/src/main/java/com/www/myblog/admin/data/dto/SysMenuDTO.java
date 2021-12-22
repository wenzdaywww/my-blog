package com.www.myblog.admin.data.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 菜单编码
     */
    private String menuCode;
    /**
     * 菜单路径
     */
    private String menuUrl;
    /**
     * 菜单角色
     */
    private String roleCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * router的path路径
     */
    private String vuePath;
    /**
     * 父级菜单ID
     */
    private Long parentId;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单序号
     */
    private Integer menuOrder;
    /**
     * 菜单类型：1目录菜单，2权限菜单，3vue路由
     */
    private String menuType;
    /**
     * 菜单归属模块
     */
    private String module;
    /**
     * 是否删除：1删除，0未删除
     */
    private String isDelete;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *  子菜单
     */
    List<SysMenuDTO> children;
}