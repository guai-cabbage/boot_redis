package com.shuai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 *  @Description： 搭建基本环境
 *  @Author: guai
 *  @Date：2020/6/27 17:11
**/

/**
 * 测试缓存
 *     原理：CacheManager====Cache 缓存组件来实际给缓存中存取数据
 *     1)引入redis的stater，容器中保存的时RedisCacheManager
 *     2)RedisCacheManager创建RedisCache来作为缓存组件，RedisCache通过操作redis缓存数据
 *         1)引入了redis的stater，cacheManager变为RedisCacheManager;
 *         2)默认创建的RedisCacheManager 操作redis的时候使用的时RedisTemplate<Object,Object>
 *         3)RedisTemplate<Object,Object>是默认使用jdk的序列化机制
 *     3）自定义CacheManager；
 *
 *
 *
 */
@MapperScan("com.shuai.mapper")
@SpringBootApplication
//开启基于注解的缓存
@EnableCaching
public class BootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootCacheApplication.class, args);
    }

}
