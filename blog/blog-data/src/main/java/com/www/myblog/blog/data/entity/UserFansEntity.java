package com.www.myblog.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>@Description 用户粉丝信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 23:07 </p>
 */
@Data
@TableName("USER_FANS")
public class UserFansEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 用户粉丝信息主键
    */
    @TableId(value = "UF_ID",type = IdType.AUTO)
    private Long ufId;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 粉丝ID
     */
    @TableField("FANS_ID")
    private String fansId;
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