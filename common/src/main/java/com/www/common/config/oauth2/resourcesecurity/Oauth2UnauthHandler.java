package com.www.common.config.oauth2.resourcesecurity;

import com.alibaba.fastjson.JSON;
import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.pojo.dto.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 拒绝访问异常处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 22:25 </p>
 */
@Slf4j
@Component
@ConditionalOnClass(AccessDeniedHandler.class)
public class Oauth2UnauthHandler implements AccessDeniedHandler {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    /**
     * <p>@Description 拒绝访问异常处理  </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 22:27 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return void
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String token = httpServletRequest.getHeader(JwtTokenConverter.TOKEN_KEY);
        TokenInfoDTO tokenDTO = jwtTokenConverter.decodeToken(token);
        log.info("4、请求的角色拒绝访问，角色权限信息：{}，拒绝原因：{}",JSON.toJSONString(tokenDTO),e.getMessage());
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.UNAUTHORIZED,e.getMessage());
        Cookie cookie = WebUtils.getCookie(httpServletRequest, JwtTokenConverter.TOKEN_KEY);
        if(cookie != null){
            cookie.setValue(null);
            httpServletResponse.addCookie(cookie);
        }
        httpServletResponse.setStatus(ResponseDTO.RespEnum.UNAUTHORIZED.getCode());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
