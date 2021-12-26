package com.www.common.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.pojo.dto.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 认证失败时的异常处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/15 20:39 </p>
 */
@Slf4j
@Component
public class Oauth2AuthRejectHandler implements AuthenticationEntryPoint {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    /**
     * <p>@Description 认证失败时的异常处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:30 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return void
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String token = httpServletRequest.getHeader(JwtTokenConverter.TOKEN_KEY);
        TokenInfoDTO tokenDTO = jwtTokenConverter.decodeToken(token);
        log.info("=====> 4、请求认证失败，认证信息：{}，失败原因：{}",JSON.toJSONString(tokenDTO),e.getMessage());
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.UNAUTHORIZED,"认证失败");
        Cookie cookie = WebUtils.getCookie(httpServletRequest,"");
        httpServletResponse.setStatus(ResponseDTO.RespEnum.UNAUTHORIZED.getCode());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
