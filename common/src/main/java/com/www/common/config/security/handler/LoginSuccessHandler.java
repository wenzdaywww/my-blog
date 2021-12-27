package com.www.common.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.config.redis.RedisOperation;
import com.www.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
@Slf4j
//@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    /** 使用redis保存用户的token的key前缀 **/
    @Value("${jwt.user-prefix}")
    private String redisUserPrefix;
    /**  返回客户段cookie中的token的name **/
    public static final String COOKIE_TOKEN = "token";
    /** 是否免登录 **/
    private static final String IS_REMENMBER_ME = "1";
    /** cookie免登录有效天数 **/
    @Value("${jwt.cookie-day}")
    private int COOKIE_AGE;
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
        log.info("=====> 3、security登录认证成功处理");
        //获取登录成功后的UserDetail对象
        User user = (User)authentication.getPrincipal();
        Map<String,Object> chaims = new HashMap<>();
        chaims.put(TokenUtils.USERID,user.getUsername());
        //设置token过期时间
        int expirationTime = StringUtils.equals(IS_REMENMBER_ME,request.getParameter("rmb"))
                ? 60*60*24*COOKIE_AGE //天数
                : TokenUtils.getExpirationTime(); //秒
        //生成token
        Map<String,String> tokenMap = TokenUtils.generateToken(chaims);
        //将token添加到redis中
        RedisOperation.set(redisUserPrefix + ":" + user.getUsername(),tokenMap.get(TokenUtils.TOKEN), expirationTime);
        //数据返回
        ResponseDTO<Map> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,tokenMap);
        Cookie cookie = new Cookie(COOKIE_TOKEN,tokenMap.get(TokenUtils.TOKEN));
        cookie.setMaxAge(expirationTime);
        response.addCookie(cookie);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader(TokenUtils.AUTHORIZATION,tokenMap.get(TokenUtils.TOKEN));
        response.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
