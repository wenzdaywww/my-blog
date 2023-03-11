package com.www.myblog.base.data.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>@Description 项目配置属性类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 20:37 </p>
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "com.www.base")
public class BaseProperties {
    /** 资源服务ID的url的scope分布式锁key **/
    @Value("${com.www.base.url-scope-lock}")
    private String urlScopeLock;
    /** 资源服务ID的url的scope的redis的key前缀,完整格式： OAUTH2:RESOURCE_ID:URL_SCOPE:资源服务ID **/
    @Value("${com.www.base.url-scope-prefix}")
    private String urlScopePrefix;
    /** 数据字典的分布式锁key **/
    @Value("${com.www.base.code-data-lock}")
    private String codeDataLock;
    /** 数据字典的key **/
    @Value("${com.www.base.code-data-key}")
    private String codeDataKey;
}
