package com.www.common.config.oauth2.authorize.config;

import com.www.common.config.oauth2.authorize.store.JwtTokenConverter;
import com.www.common.config.oauth2.resuorce.resourcesecurity.Oauth2Extractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <p>@Description 认证服务器token存储方式配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 12:21 </p>
 */
@Configuration
public class AuthorizeTokenConfig {
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
    /**
     * <p>@Description 自定义token获取器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/27 23:39 </p>
     * @return com.www.common.config.oauth2.resuorce.resourcesecurity.Oauth2Extractor
     */
    @Bean
    public Oauth2Extractor oauth2Extractor(){
        return new Oauth2Extractor();
    }
}
