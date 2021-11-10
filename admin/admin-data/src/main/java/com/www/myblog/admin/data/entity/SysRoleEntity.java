package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>@Description 角色信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:24 </p>
 */
@Data
public class SysRoleEntity implements Serializable {
    /**
    * 角色主键
    */
    private Long roleId;

    /**
    * 角色昵称
    */
    private String roleName;

    /**
    * 角色描述
    */
    private String description;

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