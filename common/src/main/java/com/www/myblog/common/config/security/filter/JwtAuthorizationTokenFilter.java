package com.www.myblog.common.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.www.myblog.common.config.security.handler.LoginSuccessHandler;
import com.www.myblog.common.config.security.impl.UserDetailsServiceImpl;
import com.www.myblog.common.utils.CookisUtils;
import com.www.myblog.common.utils.RedisUtils;
import com.www.myblog.common.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>@Description token验证拦截器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/16 21:06 </p>
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    private static Logger LOG = LoggerFactory.getLogger(JwtAuthorizationTokenFilter.class);
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.user-prefix}")
    private String redisUserPrefix;
    /** 密钥(www362412) */
    @Value("${jwt.secret-key}")
    private String SECRET_KEY ;
    /**  过期时间（秒） */
    @Value("${jwt.expire-time-second}")
    private int EXPIRE_TIME;
    /** 设置token过期时间和密钥 **/
    @PostConstruct
    public void setSecretAndExpireTime(){
        TokenUtils.setSecretAndExpireTime(EXPIRE_TIME,SECRET_KEY);
    }
    /**
     * <p>@Description token验证 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 19:24 </p>
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @return void
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = CookisUtils.getCookieValue(httpServletRequest,LoginSuccessHandler.COOKIE_TOKEN);
        LOG.info("-----> 1、访问token验证，token={}",token);
        Map<String,Object> map = TokenUtils.validateTokenAndGetClaims(token);
        if(map != null && map.size() > 0){
            String userId = String.valueOf(map.get(TokenUtils.USERID));
            String tokenKey = redisUserPrefix + ":" + userId;
            //判断redis中的token是否存在且token值相等，存在则说明token有效
            if(RedisUtils.hasKey(tokenKey)
                    && StringUtils.equals((String)map.get(TokenUtils.AUTHORIZATION),RedisUtils.get(tokenKey))
                    && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                if(userDetails != null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
