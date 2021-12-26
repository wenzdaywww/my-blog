package com.www.myblog.base.data.constants;

/**
 * <p>@Description redis的key前缀枚举
 * key的命名规则：项目名:表名或业务:用于区分key的字段（如主键列名）:主键值
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/14 21:53 </p>
 */
public class RedisKeyConstant {
    /** 资源服务ID的url的scope分布式锁key **/
    public static final String URL_SCOPE_LOCK =  "MY-BASE:LOCK:URL_SCOPE:SYS_MENU";

}
