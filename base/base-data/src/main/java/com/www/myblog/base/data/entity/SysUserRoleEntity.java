package com.www.myblog.base.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>@Description 用户角色信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
@Accessors(chain = true)//开启链式编程
@TableName("SYS_USER_ROLE")
public class SysUserRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 用户角色主键
    */
    @TableId(value = "SUR_ID",type = IdType.AUTO)
    private Long surId;
    /**
    * 用户主键ID
    */
    @TableField("SU_ID")
    private Long suId;
    /**
    * 角色ID
    */
    @TableField("ROLE_ID")
    private Long roleId;
    /**
    * 更新时间
    */
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
    * 创建时间
    */
    @TableField("CREATE_TIME")
    private Date createTime;
}