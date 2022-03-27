package com.www.uaa.config.authorize.core;

import com.www.common.config.oauth2.resource.Oauth2Properties;
import com.www.common.config.oauth2.token.Oauth2TokenConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@Description 认证服务方token存储方式配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 12:21 </p>
 */
@Configuration
@EnableConfigurationProperties(value = Oauth2Properties.class)
public class AuthorizeTokenConfig extends Oauth2TokenConfig {
}
