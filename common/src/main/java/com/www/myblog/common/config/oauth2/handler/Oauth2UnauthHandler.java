package com.www.myblog.common.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.TokenDTO;
import com.www.myblog.common.pojo.TokenInfoDTO;
import com.www.myblog.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>@Description 拒绝访问异常处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 22:25 </p>
 */
@Slf4j
@Component
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
        log.info("=====> 3、请求的角色拒绝访问，角色权限信息：{}，拒绝原因：{}",JSON.toJSONString(tokenDTO),e.getMessage());
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.FORBIDDEN,e.getMessage());
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
