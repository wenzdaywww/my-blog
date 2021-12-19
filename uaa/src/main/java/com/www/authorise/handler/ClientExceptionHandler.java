package com.www.authorise.handler;

import com.alibaba.fastjson.JSON;
import com.www.authorise.data.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 匿名用户访问无权限资源时的异常处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/15 20:39 </p>
 */
public class ClientExceptionHandler implements AuthenticationEntryPoint {
    private static Logger LOG = LoggerFactory.getLogger(ClientExceptionHandler.class);
    /**
     * <p>@Description 匿名用户访问无权限资源时的异常处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:30 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return void
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info("=====> 客户端访问异常，异常信息：{}",e.getMessage());
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.FORBIDDEN,"客户端访问异常");
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
