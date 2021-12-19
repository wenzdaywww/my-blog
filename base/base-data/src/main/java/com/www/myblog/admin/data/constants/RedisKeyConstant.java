package com.www.myblog.admin.data.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * <p>@Description redis的key前缀枚举
 * key的命名规则：项目名:表名或业务:用于区分key的字段（如主键列名）:主键值
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/14 21:53 </p>
 */
public class RedisKeyConstant {
    /** 所有请求权限分布式锁key **/
    public static final String AUTHORITY_MENU_LOCK =  "MY-BLOG:LOCK:AUTHORITY:SYS_MENU";
    /** 所有请求权限 **/
    public static final String AUTHORITY_MENU =  "MY-BLOG:AUTHORITY:SYS_MENU:MENU_TYPE_2";

}
