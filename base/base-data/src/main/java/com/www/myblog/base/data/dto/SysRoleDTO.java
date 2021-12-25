package com.www.myblog.base.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 角色信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
public class SysRoleDTO implements Serializable {
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
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
}