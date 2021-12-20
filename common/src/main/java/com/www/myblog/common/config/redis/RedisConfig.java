package com.www.myblog.common.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.www.myblog.common.config.oauth2.ResourceSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>@Description redis配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:05 </p>
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisConfig {
    private static Logger LOG = LoggerFactory.getLogger(RedisConfig.class);
    /**
     * <p>@Description 自定义redisTemplate </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:06 </p>
     * @param redisConnectionFactory redis连接工厂
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        LOG.info("=====> 配置自定义redisTemplate");
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper obj = new ObjectMapper();
        obj.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        obj.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(obj);
        //String序列化配置
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用string 序列化
        template.setKeySerializer(stringRedisSerializer);
        //hash的key用string 序列化
        template.setHashKeySerializer(stringRedisSerializer);
        //value采用jackson序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
