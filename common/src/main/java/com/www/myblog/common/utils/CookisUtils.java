package com.www.myblog.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/12 22:07 </p>
 */
public class CookisUtils {
    /**
     * <p>@Description 获取cookie的值 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/12 22:08 </p>
     * @param request 请求
     * @param cookieName cookie名
     * @return java.lang.String cookie值
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName) {
        if(request == null){
            return null;
        }
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (int i = 0; cookies != null && i < cookies.length; i++){
            if(StringUtils.equals(cookies[i].getName(), cookieName)){
                token = cookies[i].getValue();
                break;
            }
        }
        return token;
    }
}
