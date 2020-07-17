package com.shuai.redis;

import com.shuai.bean.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *  @Description：boot与redis 的整合
 *  @Author: guai
 *  @Date：2020/7/8 22:42
**/
@SpringBootTest
public class BootRedisTest {
    //操作k-v 都是字符串
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //操作k-v都是对象的
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object,Employee> empRedisTemplate;

    /**
     * Redis 常见的五大数据类型
     * String（字符串）、list（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * StringRedisTemplate.opsForVlaue()[String(字符串)]
     * stringRedisTemplage.opsForList()[List（列表）]
     * stringRedisTemplate.opsForHash（）[Hash(散列)]
     * stringRedisTemplate.opsForZset()[ZSet(有序集合)]
     *
     */
    @Test
    public void redisTest1(){
//        //向缓存中保存字符串数据
//        stringRedisTemplate.opsForValue().append("msg","hello");
//        //从缓存中取字符串数据
//        String result=stringRedisTemplate.opsForValue().get("msg");
//        System.out.println("result:"+result);

        stringRedisTemplate.opsForList().leftPush("mylist","1");
        stringRedisTemplate.opsForList().leftPush("mylist","2");
        stringRedisTemplate.opsForList().leftPush("mylist","3");
        stringRedisTemplate.opsForList().leftPush("mylist","4");

    }
    //测试对象
    @Test
    public void redisForObjTest(){
        Employee employee=new Employee();
        employee.setdId(1);
        employee.setLastName("guai");
        employee.setEmail("111");
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
       // redisTemplate.opsForValue().set("emp-01",employee);

        //1、将数据以json的方式i保存
        //1）自己将对象转为json
        //2）redisTemplate默认的序列化规则
        empRedisTemplate.opsForValue().set("emp-02",employee);
    }


}
