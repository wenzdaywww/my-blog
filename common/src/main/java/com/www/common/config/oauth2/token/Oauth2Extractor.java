package com.www.common.config.oauth2.token;

import com.www.common.config.oauth2.util.RedisTokenHandler;
import com.www.common.pojo.dto.token.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>@Description 自定义token获取器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/26 16:14 </p>
 */
@Slf4j
public class Oauth2Extractor extends BearerTokenExtractor {
    /** 保存到cookie的access_token的key **/
    public static final String COOKIES_ACCESS_TOKEN = "access_token";
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    /**
     * <p>@Description 设置token获取方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/26 16:32 </p>
     * @param request 请求
     * @return org.springframework.security.core.Authentication
     */
    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = this.getToken(request);
        if(StringUtils.isBlank(tokenValue)){
            log.info("1、获取请求中的token单点登录验证不通过，请求中的token不存在");
            return null;
        }
        TokenInfoDTO tokenInfoDTO = jwtTokenConverter.decodeToken(tokenValue);
        if(!RedisTokenHandler.isEffectiveToken(tokenInfoDTO,tokenValue)){
            log.info("1、获取请求中的token单点登录验证不通过，请求中的token已失效");
            return null;
        }
        log.info("1、获取请求中的token单点登录验证通过");
        if (tokenValue != null) {
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(tokenValue, "");
            return authentication;
        }
        return null;
    }
    /**
     * <p>@Description 获取token </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/27 21:17 </p>
     * @param request 请求
     * @return java.lang.String
     */
    public String getToken(HttpServletRequest request){
        //token获取优先级
        //1、从请求头header中获取Authorization参数值，参数值必须是【Bearer 】开头
        //2、从请求参数中获取access_token的参数值
        String tokenValue = super.extractToken(request);
        //3、从cookie中获取access_token
        if(StringUtils.isBlank(tokenValue)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (int i = 0; i < cookies.length; i++){
                    if (StringUtils.equals(COOKIES_ACCESS_TOKEN,cookies[i].getName()) && StringUtils.isNotBlank(cookies[i].getValue())){
                        return cookies[i].getValue();
                    }
                }
            }else {
                log.info("请求的cookie为空");
            }
        }
        return null;
    }
}
