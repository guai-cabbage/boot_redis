package com.shuai.config;

import com.shuai.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException{
        RedisTemplate<Object,Employee> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<Employee> serializer= new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(serializer);
        System.out.println("my redis");
        return template;
    }


}
