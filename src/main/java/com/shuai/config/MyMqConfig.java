package com.shuai.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @Description：自定义MessageConverter
 *  @Author: guai
 *  @Date：2020/7/20 19:30
**/
@Configuration
public class MyMqConfig {
    //MessageConverter <-AbstractMessageConverter<-Jackson2JsonMessageConverter
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
