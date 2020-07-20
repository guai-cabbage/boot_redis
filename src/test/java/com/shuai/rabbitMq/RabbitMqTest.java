package com.shuai.rabbitMq;

import com.shuai.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    Employee employee=new Employee();
    @Before
    public void init(){

        employee.setId(1);
        employee.setEmail("employee.com");
        employee.setLastName("guai");
        employee.setdId(1);
        employee.setGender(1);
    }

    /**
     * 1、单播（点对点）
     */
    @Test
    public void directSendTest(){
        //message 需要自己构造;定义消息体内容
        //rabbitTemplate.send(exchage,routeKey,message);
        //只需要传入要发送的对象，自动序列化发送给rabbitmq
        //rabbitTemplate.convertAndSend(exchage,routeKey,object);
        //exchange:转换器名称
        //routingKey:路由键
//        Map<String,Object> map=new HashMap<>();
//        map.put("msg","boot and mq");
//        map.put("data", Arrays.asList("hello world",123,true));

        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","guai",employee);
    }

    //从队列中获取数据
    @Test
    public void directReceiveTest(){

        System.out.println("-----------------------------------");
        //指定消息队列的名称 --先进先出
        Object obj= rabbitTemplate.receiveAndConvert("guai");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }

    /**
     * 广播(fanout)
     *
     */
    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("exchange.fanout","",employee);
    }

}
