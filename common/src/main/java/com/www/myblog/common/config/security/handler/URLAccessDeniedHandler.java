package com.www.myblog.common.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.config.security.filter.SecurityAccessDecisionManager;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 访问权限角色验证不通过处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 22:25 </p>
 */
@Component
public class URLAccessDeniedHandler implements AccessDeniedHandler {
    private static Logger LOG = LoggerFactory.getLogger(SecurityAccessDecisionManager.class);

    /**
     * <p>@Description 访问权限角色验证不通过处理  </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 22:27 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return void
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        LOG.info("-----> 5、访问权限角色验证不通过");
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.FORBIDDEN,"无权限访问");
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
