package com.www.myblog.admin.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.utils.TokenUtilHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>@Description security登录认证成功处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:12 </p>
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);
    private TokenUtilHandler tokenUtilHandler;
    /**
     * <p>@Description security登录认证成功处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:12 </p>
     * @param request 请求报文
     * @param response 响应报文
     * @param authentication 身份验证
     * @return void
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOG.info("-----> security登录认证成功处理");
        //获取登录成功后的UserDetail对象
        User user = (User)authentication.getPrincipal();
        Map<String,Object> chaims = new HashMap<>();
        chaims.put(TokenUtilHandler.USERNAME,user.getUsername());
        //生成token
        Map<String,String> tokenMap = tokenUtilHandler.generateToken(chaims);
        //数据返回
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,tokenMap.toString());
        response.setContentType("application/json;charset=utf-8");
        response.setHeader(TokenUtilHandler.HEADER_STRING,tokenMap.get(TokenUtilHandler.TOKEN));
        response.getWriter().write(JSON.toJSONString(responseDTO));
    }

    @Autowired
    public void setTokenUtilHandler(TokenUtilHandler tokenUtilHandler){
        this.tokenUtilHandler = tokenUtilHandler;
    }
}
