package com.sesacthon.infra.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//redis연결을 위한 설정파일
@Configuration
public class RedisConfig {
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    //redis로 연결하기 위한 host, port설정
    return new LettuceConnectionFactory("127.0.0.1", 6379);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate() {
    final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
    stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
    stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
    return stringRedisTemplate;
  }
}