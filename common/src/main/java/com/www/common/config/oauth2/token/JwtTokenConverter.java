package com.www.common.config.oauth2.token;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.dto.token.TokenInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private Oauth2Extractor oauth2Extractor;

    /**
     * <p>@Description token解析 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 21:02 </p>
     * @param token 令牌
     * @return token信息
     */
    public TokenInfoDTO decodeToken(String token){
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = super.decode(token.replace(TOKEN_PREFIX,""));
        TokenInfoDTO tokenInfoDTO = map != null ? JSON.parseObject(JSON.toJSONString(map),TokenInfoDTO.class) : null;
        return tokenInfoDTO;
    }
    /**
     * <p>@Description token解析 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/24 21:02 </p>
     * @param request 请求信息
     * @return token信息
     */
    public TokenInfoDTO decodeToken(HttpServletRequest request){
        if(request == null){
            return null;
        }
        String token = oauth2Extractor.getToken(request);
        return decodeToken(token);
    }
    /**
     * <p>@Description 从当前请求头中获取当前登录的用户ID </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 16:48 </p>
     * @param request 请求信息
     * @return java.lang.String 用户ID
     */
    public String getUserId(HttpServletRequest request){
        TokenInfoDTO tokenDTO = this.decodeToken(request);
        return tokenDTO != null ? tokenDTO.getUser_name() : null;
    }
    /**
     * <p>@Description 从当前请求头中获取当前登录的用户ID </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 16:48 </p>
     * @return java.lang.String 用户ID
     */
    public String getUserId(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletRequest request = attributes.getRequest();
            return this.getUserId(request);
        }
        return null;
    }
}
