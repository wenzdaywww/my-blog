package com.www.common.config.aop;

import com.alibaba.fastjson.JSON;
import com.www.common.pojo.constant.CharConstant;
import com.www.common.pojo.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * <p>@Description Controller层的AOP配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 20:08 </p>
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "com.www.common.ctl-aop",name = "enable")
public class ControllerAop {
    private NumberFormat numberFormat;
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 17:59 </p>
     * @return
     */
    public ControllerAop(){
        log.info("开启controller层的AOP日志拦截");
        numberFormat = NumberFormat.getNumberInstance();
        // 保留两位小数
        numberFormat.setMaximumFractionDigits(3);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        numberFormat.setRoundingMode(RoundingMode.UP);
    }
    /**
     * <p>@Description 设置controller切入点 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 21:22 </p>
     * @return void
     */
    @Pointcut(value="execution(public * com.www..*.controller..*.*(..))") // 切入点表达式
    public void pointcut() {}
    /**
     * <p>@Description 环绕切入，打印请求信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 21:22 </p>
     * @param pjd
     * @return java.lang.Object
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjd) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //获取当前请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取方法全量名
        String controllerMethod = pjd.getSignature().toString();
        Method targetMethod = ((MethodSignature) (pjd.getSignature())).getMethod();
        //获取方法参数名称
        Parameter[] paramName = targetMethod.getParameters();
        Class<?>[] paramType = targetMethod.getParameterTypes();
        Logger log = LoggerFactory.getLogger(pjd.getSignature().getClass().getName());// 初始化日志打印
        Object[] paramValue = pjd.getArgs();// 获取方法参数值
        //获取请求参数集合并进行遍历拼接
        String requestText = CharConstant.LEFT_BRACE;
        for (int i = 0; i < paramName.length && i < paramValue.length && i < paramType.length;  i++) {
            String value = "";
            if(paramValue[i] instanceof  MultipartFile){
                value = paramValue[i] != null ? ((MultipartFile) paramValue[i]).getOriginalFilename() : CharConstant.EMPTY;

            }else {
                value = JSON.toJSONString(paramValue[i]);
            }
            value = StringUtils.length(value) > 256 ? "<longText>" : value;
            if(i != paramName.length -1){
                requestText += paramName[i].getName() + CharConstant.EQUAL + value + CharConstant.COMMA;
            }else {
                requestText += paramName[i].getName() + CharConstant.EQUAL + value ;
            }
        }
        requestText += CharConstant.RIGHT_BRACE;
        try {
            Object result = pjd.proceed();// 执行目标方法
            stopWatch.stop();
            log.info("请求:{} 调用{}方法执行耗时:{}秒。请求报文:{}，响应报文:{}",request.getRequestURI(),
                    controllerMethod,numberFormat.format(stopWatch.getTotalTimeSeconds()),requestText,JSON.toJSONString(result));
            return result;
        }catch (Exception e){
            log.error("请求:{} 调用{}方法发生异常。请求报文:{}，异常信息:",request.getRequestURI(), controllerMethod,requestText,e);
            return new ResponseDTO<>(ResponseDTO.RespEnum.UNDEFINE);
        }
    }
}
