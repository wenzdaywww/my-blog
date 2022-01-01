package com.www.common.config.druid.datasoure;

import com.alibaba.druid.pool.DruidDataSource;
import com.www.common.config.druid.interfaces.IWriteDataSoure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 写权限数据源实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/30 21:56 </p>
 */
@Slf4j
@Primary//优先使用master
@Component
@ConditionalOnClass(DruidDataSource.class)
@ConfigurationProperties(prefix = "com.www.common.druid.write")
//com.www.common.druid.enable=true才开启多数据源配置
@ConditionalOnProperty(prefix = "com.www.common.druid.write",name = {"url"})
public class WriteDataSource extends DruidDataSource implements IWriteDataSoure {
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/30 23:05 </p>
     * @return
     */
    public WriteDataSource(){
        super();
        log.info("加载写权限数据源");
    }
}
