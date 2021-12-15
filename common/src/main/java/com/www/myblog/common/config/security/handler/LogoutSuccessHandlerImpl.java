package com.www.myblog.common.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.utils.CookisUtils;
import com.www.myblog.common.utils.RedisUtils;
import com.www.myblog.common.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

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
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private static Logger LOG = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);
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
        LOG.info("=====> 6、security退出成功");
        String token = CookisUtils.getCookieValue(httpServletRequest,LoginSuccessHandler.COOKIE_TOKEN);
        Map<String,Object> map = TokenUtils.validateTokenAndGetClaims(token);
        //获取token，删除redis中的token
        if(map != null && map.size() > 0) {
            String userId = String.valueOf(map.get(TokenUtils.USERID));
            RedisUtils.deleteKey(redisUserPrefix + ":" + userId);
        }
        ResponseDTO<String> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,"退出成功");
        Cookie cookie = new Cookie(LoginSuccessHandler.COOKIE_TOKEN,null);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseDTO));
    }
}
