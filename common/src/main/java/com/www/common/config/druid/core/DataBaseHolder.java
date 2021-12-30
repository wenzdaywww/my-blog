package com.www.common.config.druid.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

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
    /** 随机数 **/
    private static Random random = new Random();
    /** 数据源线程局部变量 **/
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    /**
     * <p>@Description 往线程中设置写权限数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:39 </p>
     * @return void
     */
    public static void setWriteDataBaseType(){
        //随机获取其中一个写权限的数据源
        if(MultipleDataSourceConfig.getWriteNum() != 0){
            int index = random.nextInt(MultipleDataSourceConfig.getWriteNum());
            contextHolder.set(MultipleDataSourceConfig.WRITE_DATA_SOURCE_PREFIX + index);
        }
    }
    /**
     * <p>@Description 往线程中设置读权限数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:39 </p>
     * @return void
     */
    public static void setReadDataBaseType(){
        //有配置读权限数据源则从中随机获取
        if(MultipleDataSourceConfig.getReadNum() != 0){
            //随机获取其中一个读权限的数据源
            int index = random.nextInt(MultipleDataSourceConfig.getReadNum());
            contextHolder.set(MultipleDataSourceConfig.READ_DATA_SOURCE_PREFIX + index);
        }
    }
    /**
     * <p>@Description 从容器中获取数据源 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:40 </p>
     * @return com.www.demo.druid.config.DataBaseContextHolder.DataBaseType
     */
    public static String getDataBaseType(){
       return contextHolder.get() == null ? MultipleDataSourceConfig.READ_DATA_SOURCE_PREFIX + 0 : contextHolder.get();
    }
    /**
     * <p>@Description 清除容器的数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:40 </p>
     * @return void
     */
    public static void clearDataBaseType(){
        contextHolder.remove();
    }
}
