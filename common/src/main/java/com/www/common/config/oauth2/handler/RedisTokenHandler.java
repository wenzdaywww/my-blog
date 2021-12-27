package com.www.common.config.oauth2.handler;

import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.dto.TokenInfoDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>@Description redis的token处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/27 21:54 </p>
 */
public class RedisTokenHandler {
    /** redis的key分隔符 **/
    private static final String SEPARATOR = ":";
    /** redis的key前缀 **/
    private static final String PREFIX = "oauth2_token" + SEPARATOR;

    /**
     * <p>@Description 保存用户登录的token到redis中 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/27 21:56 </p>
     * @param tokenInfo token信息
     * @param token token值
     * @param expiresSeconds 令牌有效时间
     * @return boolean true保存成功，false失败
     */
    public static boolean setUserIdToken(TokenInfoDTO tokenInfo,String token,int expiresSeconds){
        if(StringUtils.isBlank(token) || tokenInfo == null){
            return false;
        }
        //将token保存到redis中
        //用户key格式：oauth2_token:客户端ID:用户ID
        String key = PREFIX + tokenInfo.getClient_id() + SEPARATOR + tokenInfo.getUser_name();
        RedisOperation.set(key,token,expiresSeconds);
        return true;
    }
    /**
     * <p>@Description 判断当前token是否是redis中有效的token </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/27 21:58 </p>
     * @param tokenInfo token信息
     * @param token token值
     * @return boolean true有效，false无效
     */
    public static boolean isEffectiveToken(TokenInfoDTO tokenInfo,String token){
        if(tokenInfo == null || StringUtils.isBlank(token)){
            return false;
        }
        //用户key格式：oauth2_token:客户端ID:用户ID
        String key = PREFIX + tokenInfo.getClient_id() + SEPARATOR + tokenInfo.getUser_name();
        return  (RedisOperation.hasKey(key) && StringUtils.equals(token,RedisOperation.get(key)));
    }
}
