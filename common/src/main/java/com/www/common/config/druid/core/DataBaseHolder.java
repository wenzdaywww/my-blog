package com.www.common.config.druid.core;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>@Description 配置数据源操作
 * 实现一个即时切换主从数据源的标识并且能保证线程安全的基础下操作数据源（原因是并发会影响数据源
 * 的获取分不清主从，造成在从库进行写操作，影响mysql（mariadb）数据库的机制，导致
 * 服务器异常。这里使用threadocal来解决这个问题）
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 20:38 </p>
 */
@Slf4j
public class DataBaseHolder {
    /**
     * <p>@Description 数据源类型 </p>
     * <p>@Version 1.0 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:39 </p>
     */
    public enum DataBaseType{
        /** 写数据库 **/
        WRITE,
        /** 读数据库1 **/
        READ_ONE,
        /** 读数据库2 **/
        READ_TWO
    }
    /**  读数据库的枚举数组 **/
    private static DataBaseType[] readData = {DataBaseType.READ_ONE,DataBaseType.READ_TWO};
    /** 数据源线程局部变量 **/
    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<DataBaseType>();
    /**
     * <p>@Description 往线程中设置写权限数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:39 </p>
     * @return void
     */
    public static void setWriteDataBaseType(){
        contextHolder.set(DataBaseHolder.DataBaseType.WRITE);
//        log.info("=====> 往线程中设置写权限数据源类型");
    }
    /**
     * <p>@Description 往线程中设置读权限数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:39 </p>
     * @return void
     */
    public static void setReadDataBaseType(){
        int index = (int)(10*Math.random())%2;//随机产生0或1
//        log.info("====> 往线程中设置读权限数据源类型,{}",index);
        contextHolder.set(readData[index]);
    }
    /**
     * <p>@Description 从容器中获取数据源 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:40 </p>
     * @return com.www.demo.druid.config.DataBaseContextHolder.DataBaseType
     */
    public static DataBaseType getDataBaseType(){
//       log.info("=====> 从容器中获取数据源,{}",contextHolder.get());
       return contextHolder.get() == null ? DataBaseType.WRITE : contextHolder.get();
    }
    /**
     * <p>@Description 清除容器的数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:40 </p>
     * @return void
     */
    public static void clearDataBaseType(){
//        log.info("=====> 清除容器的数据源类型");
        contextHolder.remove();
    }
}
