package com.www.myblog.admin.config.security.config;

import com.www.myblog.common.utils.TokenUtilHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@Description jwt工具配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/16 20:54 </p>
 */
@Configuration
public class JwtConfig {
    /** 密钥(www362412) */
    private final String secret = "6969fc2fd7e864ca8152860ca8b38e76";
    /**  过期时间（秒） */
    public static final long EXPIRE_TIME = 1800L;

    @Bean
    public TokenUtilHandler tokenUtilHandler() {
        return new TokenUtilHandler(EXPIRE_TIME,secret);
    }

}
