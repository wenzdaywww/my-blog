package com.www.common.config.druid.core;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.aspectj.util.SoftHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * <p>@Description mybatis的数据源配置
 * DataSoureConfig这个文件在DataSourceConfiguration加载完成之后再加载MybatisConfiguration
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 20:47 </p>
 */
@Slf4j
@Configuration
@AutoConfigureAfter(DataSoureConfig.class)
public class MyBatisDataSourceConfig extends MybatisPlusAutoConfiguration {
    private static Logger LOG = LoggerFactory.getLogger(MyBatisDataSourceConfig.class);
    @Resource(name = "writeDataSource")
    private DataSource writeDataSource;
    @Resource(name = "readDataSourceOne")
    private DataSource readDataSourceOne;
    @Resource(name = "readDataSourceTwo")
    private DataSource readDataSourceTwo;

    public MyBatisDataSourceConfig(MybatisPlusProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider, ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider, ApplicationContext applicationContext) {
        super(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider, mybatisPlusPropertiesCustomizerProvider, applicationContext);
    }
    /**
     * <p>@Description 加载数据源类型 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:47 </p>
     * @return org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
     */
    @Bean(name = "routingDataSource")
    public AbstractRoutingDataSource routingDataSource(){
        ReadWriteDataSource proxy = new ReadWriteDataSource();
        SoftHashMap targetDataSource = new SoftHashMap<>();
        targetDataSource.put(DataBaseHolder.DataBaseType.WRITE, writeDataSource);
        targetDataSource.put(DataBaseHolder.DataBaseType.READ_ONE, readDataSourceOne);
        targetDataSource.put(DataBaseHolder.DataBaseType.READ_TWO, readDataSourceTwo);
        //默认数据源
        proxy.setDefaultTargetDataSource(writeDataSource);
        //添加2个主从数据源
        proxy.setTargetDataSources(targetDataSource);
        LOG.info("=====> 加载所有数据源");
        return proxy;
    }
    /**
     * <p>@Description 加载数据源到mybatis的SqlSessionFactory中 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:47 </p>
     * @param dataSource
     * @return org.apache.ibatis.session.SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") DataSource dataSource) throws Exception {
        LOG.info("=====> 加载mybatis的sqlSessionFactory");
        return super.sqlSessionFactory(dataSource);
    }
}
