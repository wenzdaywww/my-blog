package com.www.uaa.config.authorize.handler;

import com.alibaba.fastjson.JSON;
import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.config.oauth2.token.Oauth2Extractor;
import com.www.common.config.oauth2.util.RedisTokenHandler;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.pojo.dto.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 退出成功的处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/15 20:51 </p>
 */
@Slf4j
@Component
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {
    /** 保存到cookie的access_token的key **/
    public static final String COOKIES_ACCESS_TOKEN = "access_token";
    /** 保存到cookie的refresh_token的key **/
    public static final String COOKIES_REFRESH_TOKEN = "refresh_token";
    /** 自定义jwt的token转换器 **/
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    /** 自定义token获取器 **/
    @Autowired
    private Oauth2Extractor oauth2Extractor;

    /**
     * <p>@Description 退出成功处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:31 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @return void
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("=====> 认证服务器退出成功");
        //获取token信息
        String token = oauth2Extractor.getToken(httpServletRequest);
        TokenInfoDTO tokenInfoDTO = jwtTokenConverter.decodeToken(token);
        //删除用户登录的token到redis中
        RedisTokenHandler.deleteUserIdToken(tokenInfoDTO);
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,"退出成功");
        //清除token
        Cookie accessCookie = new Cookie(COOKIES_ACCESS_TOKEN,null);
        httpServletResponse.addCookie(accessCookie);
        Cookie refreshCookie = new Cookie(COOKIES_REFRESH_TOKEN,null);
        httpServletResponse.addCookie(refreshCookie);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
