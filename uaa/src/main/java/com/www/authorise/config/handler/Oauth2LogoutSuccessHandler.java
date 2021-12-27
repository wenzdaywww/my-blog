package com.www.authorise.config.handler;

import com.alibaba.fastjson.JSON;
import com.www.authorise.controller.OauthController;
import com.www.common.config.oauth2.handler.JwtTokenConverter;
import com.www.common.config.oauth2.handler.Oauth2Extractor;
import com.www.common.config.oauth2.handler.RedisTokenHandler;
import com.www.common.config.redis.RedisOperation;
import com.www.common.config.security.handler.LoginSuccessHandler;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.pojo.dto.TokenInfoDTO;
import com.www.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>@Description 退出成功的处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/15 20:51 </p>
 */
@Slf4j
@Component
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {
    /** 自定义jwt的token转换器 **/
    @Autowired
    private JwtTokenConverter jwtTokenConverter;

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
        log.info("=====> oauth2退出成功");
        //获取token信息
        String token = new Oauth2Extractor().getToken(httpServletRequest);
        TokenInfoDTO tokenInfoDTO = jwtTokenConverter.decodeToken(token);
        //删除用户登录的token到redis中
        RedisTokenHandler.deleteUserIdToken(tokenInfoDTO);
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,"退出成功");
        //清除token
        Cookie accessCookie = new Cookie(OauthController.COOKIES_ACCESS_TOKEN,null);
        httpServletResponse.addCookie(accessCookie);
        Cookie refreshCookie = new Cookie(OauthController.COOKIES_REFRESH_TOKEN,null);
        httpServletResponse.addCookie(refreshCookie);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
