package com.www.common.config.druid.datasoure;

import com.alibaba.druid.pool.DruidDataSource;
import com.www.common.config.druid.interfaces.IReadDataSoure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 读权限数据源1实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/30 21:56 </p>
 */
@Slf4j
@Component
@ConditionalOnClass(DruidDataSource.class)
@ConfigurationProperties(prefix = "com.www.common.druid.read-one")
//com.www.common.druid.enable=true才开启多数据源配置
@ConditionalOnProperty(prefix = "com.www.common.druid.read-one",name = {"url"})
public class ReadOneDataSource extends DruidDataSource implements IReadDataSoure {
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/30 23:05 </p>
     * @return
     */
    public ReadOneDataSource(){
        super();
        log.info("加载读权限数据源1");
    }
}
