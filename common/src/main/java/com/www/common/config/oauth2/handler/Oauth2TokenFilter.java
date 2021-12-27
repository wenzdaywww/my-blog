package com.www.common.config.oauth2.handler;

import com.www.common.config.redis.RedisOperation;
import com.www.common.pojo.dto.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description token验证拦截器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/16 21:06 </p>
 */
@Slf4j
//@Component
public class Oauth2TokenFilter extends OncePerRequestFilter {
    /** redis的key分隔符 **/
    private static final String SEPARATOR = ":";
    /** redis的key前缀 **/
    private static final String PREFIX = "oauth2_token" + SEPARATOR;
    /** 资源服务id **/
    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private Oauth2Extractor oauth2Extractor;
    @Autowired
    private JwtTokenConverter jwtTokenConverter;

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
        String token = oauth2Extractor.getToken(httpServletRequest);
        TokenInfoDTO tokenInfoDTO = jwtTokenConverter.decodeToken(token);
        log.info("=====> redis中验证token有效性");
        if(tokenInfoDTO != null){
            //用户redis的key格式
            String tokenKey = PREFIX + tokenInfoDTO.getClient_id() + SEPARATOR + tokenInfoDTO.getUser_name();
            //判断redis中的token是否存在且token值相等，存在则说明token有效
            if(RedisOperation.hasKey(tokenKey)
                    && StringUtils.equals(token, RedisOperation.get(tokenKey))
                    && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = null;
                if(userDetails != null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
