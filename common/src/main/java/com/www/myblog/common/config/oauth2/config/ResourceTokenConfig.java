package com.www.myblog.common.config.oauth2.config;

import com.www.myblog.common.config.oauth2.handler.JwtTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <p>@Description token存储方式配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 12:21 </p>
 */
@Configuration
public class ResourceTokenConfig {
    /** jwt令牌签名 **/
    @Value("${security.oauth2.authorization.jwt.key-value}")
    private String signingKey;
    /**
     * <p>@Description 配置token存储方式 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 12:41 </p>
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     */
    @Bean
    public TokenStore tokenStore(){
        //使用jwt方式存储
        return new JwtTokenStore(jwtTokenConverter());
    }
    /**
     * <p>@Description 注册jwt对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 12:42 </p>
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     */
    @Bean
    public JwtTokenConverter jwtTokenConverter(){
        JwtTokenConverter converter = new JwtTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

}
