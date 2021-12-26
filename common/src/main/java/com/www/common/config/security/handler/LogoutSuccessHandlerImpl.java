package com.www.common.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.dto.ResponseDTO;
import com.www.common.utils.RedisUtils;
import com.www.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
//@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Value("${jwt.user-prefix}")
    private String redisUserPrefix;

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
        log.info("=====> 6、security退出成功");
        Cookie cookie = WebUtils.getCookie(httpServletRequest,LoginSuccessHandler.COOKIE_TOKEN);
        String token = cookie != null ? cookie.getValue() : "";
        Map<String,Object> map = TokenUtils.validateTokenAndGetClaims(token);
        //获取token，删除redis中的token
        if(map != null && map.size() > 0) {
            String userId = String.valueOf(map.get(TokenUtils.USERID));
            RedisUtils.deleteKey(redisUserPrefix + ":" + userId);
        }
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,"退出成功");
        cookie = new Cookie(LoginSuccessHandler.COOKIE_TOKEN,null);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
