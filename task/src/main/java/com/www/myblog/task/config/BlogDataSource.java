package com.www.myblog.task.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>@Description base数据库的数据源 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/2/5 17:36 </p>
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "com.www.common.multi-database.blog")
public class BlogDataSource extends DruidDataSource {
}
