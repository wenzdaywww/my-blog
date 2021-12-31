package com.www.common.config.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@Description 配置日志全局跟踪号过滤器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/31 20:26 </p>
 */
@Slf4j
@Order(-1000)
@Component
public class TraceIdFilter extends OncePerRequestFilter {
    /** 日志跟踪号的key **/
    private static String traceId = "traceId";
    /**
     * <p>@Description 过滤器设置 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/31 21:52 </p>
     * @param httpServletRequest 请求参数
     * @param httpServletResponse 响应参数
     * @param filterChain 过滤器
     * @return void
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        MDC.put(traceId, httpServletRequest.getHeader(traceId));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
