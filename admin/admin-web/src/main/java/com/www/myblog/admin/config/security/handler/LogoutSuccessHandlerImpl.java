package com.www.myblog.admin.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.utils.RedisUtils;
import com.www.myblog.common.utils.TokenUtilHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private static Logger LOG = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);
    private TokenUtilHandler tokenUtilHandler;
    @Value("${spring.application.name}")
    private String applicationName;

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
        LOG.info("-----> security退出成功");
        Map<String,Object> map = tokenUtilHandler.validateTokenAndGetClaims(httpServletRequest);
        //获取token，删除redis中的token
        if(map != null && map.size() > 0) {
            String userId = String.valueOf(map.get(TokenUtilHandler.USERID));
            RedisUtils.deleteKey(applicationName + ":"+ TokenUtilHandler.TOKEN + ":" + userId);
        }
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,"退出成功");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }

    @Autowired
    public void setTokenUtilHandler(TokenUtilHandler tokenUtilHandler){
        this.tokenUtilHandler = tokenUtilHandler;
    }
}
