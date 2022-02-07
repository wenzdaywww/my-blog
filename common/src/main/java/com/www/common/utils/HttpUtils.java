package com.www.common.utils;

import com.www.common.config.filter.TraceIdFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>@Description http工具类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/7 23:00 </p>
 */
public class HttpUtils {
    /**
     * <p>@Description 获取当前http请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/7 23:00 </p>
     * @return javax.servlet.http.HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            return null;
        }
        return attributes.getRequest();
    }
    /**
     * <p>@Description 获取http请求头的全局跟踪号 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/7 23:03 </p>
     * @return java.lang.String 全局跟踪号
     */
    public static String getTraceId(){
        HttpServletRequest request = getRequest();
        if (request == null){
            return null;
        }
        return request.getHeader(TraceIdFilter.TRACE_ID);
    }
}
