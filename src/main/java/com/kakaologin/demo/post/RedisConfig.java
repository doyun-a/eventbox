package com.kakaologin.demo.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new org.springframework.data.redis.serializer.StringRedisSerializer());
        redisTemplate.setValueSerializer(new org.springframework.data.redis.serializer.GenericToStringSerializer<>(Integer.class));
        return redisTemplate;
    }
}