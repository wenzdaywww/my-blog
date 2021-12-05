package com.www.myblog.admin.data.dto;

import com.baomidou.mybatisplus.annotation.TableName;
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