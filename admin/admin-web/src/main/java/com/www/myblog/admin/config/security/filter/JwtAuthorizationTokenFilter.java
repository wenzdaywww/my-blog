package com.www.myblog.admin.config.security.filter;

import com.www.myblog.admin.config.security.impl.UserDetailsServiceImpl;
import com.www.myblog.common.utils.RedisUtils;
import com.www.myblog.common.utils.TokenUtilHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TokenUtilHandler tokenUtilHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Value("${spring.application.name}")
    private String applicationName;

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
        LOG.info("-----> token验证");
        Map<String,Object> map = tokenUtilHandler.validateTokenAndGetClaims(httpServletRequest);
        if(map != null && map.size() > 0){
            String userId = String.valueOf(map.get(TokenUtilHandler.USERID));
            //判断redis中的token是否存在，存在则说明token有效
            if(RedisUtils.hasKey(applicationName + ":"+ TokenUtilHandler.TOKEN + ":" + userId)
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

    @Autowired
    public void setTokenUtilHandler(TokenUtilHandler tokenUtilHandler){
        this.tokenUtilHandler = tokenUtilHandler;
    }
}
