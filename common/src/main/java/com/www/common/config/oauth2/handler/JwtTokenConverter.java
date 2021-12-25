package com.www.common.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.TokenInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * <p>@Description 自定义jwt的token转换器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/24 21:00 </p>
 */
public class JwtTokenConverter extends JwtAccessTokenConverter {
    /** 令牌参数key **/
    public static String TOKEN_KEY = "Authorization";
    /** 令牌前缀 **/
    public static String TOKEN_PREFIX = "Bearer ";
    /**
     * <p>@Description token解析 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 21:02 </p>
     * @param token 令牌
     * @return
     */
    public TokenInfoDTO decodeToken(String token){
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = super.decode(token.replace(TOKEN_PREFIX,""));
        TokenInfoDTO tokenInfoDTO = map != null ? JSON.parseObject(JSON.toJSONString(map),TokenInfoDTO.class) : null;
        return tokenInfoDTO;
    }
}
