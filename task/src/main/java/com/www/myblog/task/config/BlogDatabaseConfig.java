package com.www.myblog.task.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * <p>@Description 配置数据库的数据源，如果有多个数据库需要配置，则类似blog配置即可 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/5 18:03 </p>
 */
@Configuration
@EnableTransactionManagement//开启事务
//basePackages配置数据库的mapper.class路径
@MapperScan(basePackages = {"com.www.myblog.task.data.mapper.blog"}, sqlSessionFactoryRef = "blogSqlSessionFactory")
public class BlogDatabaseConfig extends MybatisPlusAutoConfiguration {
    @Autowired
    private BlogDataSource blogDataSource;
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/7 20:28 </p>
     * @param properties
     * @param interceptorsProvider
     * @param typeHandlersProvider
     * @param languageDriversProvider
     * @param resourceLoader
     * @param databaseIdProvider
     * @param configurationCustomizersProvider
     * @param mybatisPlusPropertiesCustomizerProvider
     * @param applicationContext
     * @return
     */
    public BlogDatabaseConfig(MybatisPlusProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider, ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider, ApplicationContext applicationContext) {
        super(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider, mybatisPlusPropertiesCustomizerProvider, applicationContext);
    }
    /**
     * <p>@Description 创建SqlSessionFactory对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/7 20:28 </p>
     * @return org.apache.ibatis.session.SqlSessionFactory
     */
    @Bean(name = "blogSqlSessionFactory")
    public SqlSessionFactory blogSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(blogDataSource);
        //配置数据库的mapper.xml位置，必要，否则会表不存在
        factory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/blog/*.xml"));
        return factory.getObject();
    }
    /**
     * <p>@Description 创建SqlSessionTemplate，必要 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/7 20:29 </p>
     * @param sqlSessionFactory SqlSessionFactory对象
     * @return org.mybatis.spring.SqlSessionTemplate
     */
    @Bean(name = "blogSqlSessionTemplate")
    public SqlSessionTemplate blogSqlSessionTemplate(@Qualifier("blogSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory);
    }
}
