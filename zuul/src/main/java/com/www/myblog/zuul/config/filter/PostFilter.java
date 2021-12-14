package com.www.myblog.zuul.config.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * <p>@Description post过滤器 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/29 21:31 </p>
 */
public class PostFilter extends ZuulFilter {
    private static Logger LOG = LoggerFactory.getLogger(PostFilter.class);
    /**
     * <p>@Description 设置过滤器类型
     *   pre：可以在请求被路由之前调用
     *   route：在路由请求时候被调用
     *   post：在route和error过滤器之后被调用
     *   error：处理请求时发生错误时被调用
     * </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:38 </p>
     * @return java.lang.String
     */
    @Override
    public String filterType() {
        return "post";
    }
    /**
     * <p>@Description 设置过滤器优先级，数字越小优先级越大 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:38 </p>
     * @return int
     */
    @Override
    public int filterOrder() {
        return 1;
    }
    /**
     * <p>@Description 设置是否开启过滤器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:38 </p>
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //过滤各种POST请求
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }
        return true;
    }
    /**
     * <p>@Description 设置过滤器规则 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/29 21:38 </p>
     * @return java.lang.Object
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
//        LOG.info("---> post的cookies={}", JSON.toJSONString(request.getCookies()));
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//根据该字段判断是否允许该请求访问。
        response.setHeader("Access-Control-Allow-Credentials","true");//用户是否可以发送、处理 cookie；
        //可以让用户拿到的字段。有几个字段无论设置与否都可以拿到的，包括：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma 。
        response.setHeader("Access-Control-Expose-Headers","X-forwared-port, X-forwarded-host");
        //服务器收到请求时，需要分别对 Origin、Access-Control-Request-Method、Access-Control-Request-Headers 进行验证
        response.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        //允许继续路由
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        // 转发headers信息
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                ctx.addZuulRequestHeader(name, values);
//                LOG.info("post的headers => {}={}",name,values);
            }
        }
        return null;
    }
}
