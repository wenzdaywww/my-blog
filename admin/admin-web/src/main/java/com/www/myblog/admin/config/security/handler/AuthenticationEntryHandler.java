package com.www.myblog.admin.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
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
 * <p>@Description 访问无权限是异常处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/15 20:39 </p>
 */
@Component
public class AuthenticationEntryHandler implements AuthenticationEntryPoint {
    private static Logger LOG = LoggerFactory.getLogger(AuthenticationEntryHandler.class);
    /**
     * <p>@Description 无权限访问处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:30 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return void
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info("-----> security无权限访问处理");
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.FORBIDDEN,"无权限访问");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
