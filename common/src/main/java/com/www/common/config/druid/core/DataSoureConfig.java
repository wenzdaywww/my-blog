package com.www.common.config.druid.core;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <p>@Description 加载多个数据源配置，如主从数据库 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 20:40 </p>
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DataSoureConfig {
    /** 数据源类型 **/
    @Value("${datasource.druid.type}")
    private Class<? extends DataSource> dataSoureType;

    /**
     * <p>@Description 配置写数据源 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:40 </p>
     * @return javax.sql.DataSource
     */
    @Bean("writeDataSource")
    @Primary//优先使用master
    @ConfigurationProperties(prefix = "datasource.druid.write")
    public DataSource writeDataSource(){
        DruidDataSource masterDataSource = (DruidDataSource)DataSourceBuilder.create().type(dataSoureType).build();
        log.info("=====> 创建写权限数据源");
        return masterDataSource;
    }
    /**
     * <p>@Description 配置读数据源 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:41 </p>
     * @return javax.sql.DataSource
     */
    @Bean("readDataSourceOne")
    @ConfigurationProperties(prefix = "datasource.druid.read-one")
    public DataSource readDataSourceOne(){
        DruidDataSource slaveDataSource = (DruidDataSource)DataSourceBuilder.create().type(dataSoureType).build();
        log.info("=====> 创建读权限数据源1");
        return slaveDataSource;
    }
    /**
     * <p>@Description 配置读数据源 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:41 </p>
     * @return javax.sql.DataSource
     */
    @Bean("readDataSourceTwo")
    @ConfigurationProperties(prefix = "datasource.druid.read-two")
    public DataSource readDataSourceTwo(){
        DruidDataSource slaveDataSource = (DruidDataSource)DataSourceBuilder.create().type(dataSoureType).build();
        log.info("=====> 创建读权限数据源2");
        return slaveDataSource;
    }
}
