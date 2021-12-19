package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>@Description 用户好友信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
@TableName("USER_FRIENDS")
public class UserFriendsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 好友主键
    */
    @TableId(value = "SUR_ID",type = IdType.AUTO)
    private Long friendsId;
    /**
    * 用户ID
    */
    @TableField("USER_ID")
    private String userId;
    /**
    * 好友ID
    */
    @TableField("FRIEND_ID")
    private String friendId;
    /**
    * 好友备注
    */
    @TableField("FRIEND_NAME")
    private String friendName;
    /**
    * 更新时间
    */
    @TableField("SYS_UPDATE_TIME")
    private Date sysUpdateTime;
    /**
    * 创建时间
    */
    @TableField("SYS_CREATE_TIME")
    private Date sysCreateTime;
}