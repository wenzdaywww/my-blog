package com.www.myblog.admin.data.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>@Description 用户好友信息 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/10 22:25 </p>
 */
@Data
public class UserFriendsEntity implements Serializable {
    /**
    * 好友主键
    */
    private Long friendsId;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 好友ID
    */
    private String friendId;

    /**
    * 好友备注
    */
    private String friendName;

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