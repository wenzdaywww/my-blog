package com.www.common.config.druid.interceptor;

import com.www.common.config.druid.core.DataBaseHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 读写分离数据源切断的拦截器-AOP注入 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 20:48 </p>
 */
@Slf4j
@Aspect
@Component
public class ReadWriteInterceptor implements Ordered {
    /**
     * <p>@Description 读数据操作AOP切面 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:49 </p>
     * @param proceedingJoinPoint 切点
     * @return java.lang.Object
     */
    @Around(value = "execution(* com.www..*.data.mapper..*.find*(..))")
    public Object readIsFind(ProceedingJoinPoint proceedingJoinPoint){
        return this.readOnly(proceedingJoinPoint);
    }
    /**
     * <p>@Description 读数据操作AOP切面 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:49 </p>
     * @param proceedingJoinPoint 切点
     * @return java.lang.Object
     */
    @Around(value = "execution(* com.www..*.data.mapper..*.select*(..))")
    public Object readOnly(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        try {
//            log.info("=====> start 连接slave数据源  ");
            DataBaseHolder.setReadDataBaseType();
            result = proceedingJoinPoint.proceed();
//            log.info("=====> end 连接slave数据源  ");
        }catch (Exception e){
            log.error("=====> 连接slave数据源Exception异常："+e.getMessage());
            //查询从数据库出现异常再从主数据库查询
            result = insert(proceedingJoinPoint);
        } catch (Throwable throwable) {
            log.error("=====> 连接slave数据源throwable异常："+throwable.getMessage());
        } finally {
            DataBaseHolder.clearDataBaseType();
//            log.info("=====> 清除slave数据源连接  ");
        }
        return result;
    }
    /**
     * <p>@Description 插入数据操作AOP切面 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:49 </p>
     * @param proceedingJoinPoint 切点
     * @return java.lang.Object
     */
    @Around(value = "execution(* com.www..*.data.mapper..*.insert*(..))")
    public Object insert(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        try {
//            log.info("=====> start 连接master数据源  ");
            DataBaseHolder.setWriteDataBaseType();
            result = proceedingJoinPoint.proceed();
//            log.info("=====> end 连接master数据源  ");
        }catch (Exception e){
            log.error("=====> 连接master数据源Exception异常："+e.getMessage());
        } catch (Throwable throwable) {
            log.error("=====> 连接master数据源throwable异常："+throwable.getMessage());
        } finally {
            DataBaseHolder.clearDataBaseType();
//            log.info("=====> 清除master数据源连接  ");
        }
        return result;
    }
    /**
     * <p>@Description 删除数据操作AOP切面 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:50 </p>
     * @param proceedingJoinPoint 切点
     * @return java.lang.Object
     */
    @Around(value = "execution(* com.www..*.data.mapper..*.delete*(..))")
    public Object delete(ProceedingJoinPoint proceedingJoinPoint){
        return insert(proceedingJoinPoint);
    }
    /**
     * <p>@Description 更新数据操作AOP切面 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:50 </p>
     * @param proceedingJoinPoint 切点
     * @return java.lang.Object
     */
    @Around(value = "execution(* com.www..*.data.mapper..*.update*(..))")
    public Object update(ProceedingJoinPoint proceedingJoinPoint){
        return insert(proceedingJoinPoint);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
