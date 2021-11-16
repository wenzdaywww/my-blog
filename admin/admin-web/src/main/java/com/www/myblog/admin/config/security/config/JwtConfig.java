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
    /** 密钥 */
    private final String secret = "A0B1C2D3E4F5G6H7I8J9KALBMCNDOEPFQ0R1S2T3U4V5W6X7Y8Z9";
    /**  过期时间（秒） */
    private final long expire = 600L;

    @Bean
    public TokenUtilHandler tokenUtilHandler() {
        return new TokenUtilHandler(expire,secret);
    }
}
