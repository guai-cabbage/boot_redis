package com.shuai.service;

import com.shuai.bean.Employee;
import com.shuai.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
//抽取缓存的公共配置9
@CacheConfig(cacheNames = "emp")
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，以后要相同的数据，直接从缓存中获取，不用调用方法；
     * CacheMapper管理多个Cache组件的，对缓存的真正crud操作在Cache组件中，每一个缓存组件有一个唯一的名字；
     * Cacheable:的几个属性
     *     cacheNames/value:指定缓存组件的名字；将方法的返回结果放在那个缓存中，是数据的方式，可以指定多个缓存；
     *     key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值（key-value）
     *          编写SpEL：#id；参数id的值 #a0  #p0  #root,args[0]
     *     keyGenerator:key的生成器；可以自己指定key的生成器的组件id
     *         key/keyGenerator：二选一使用
     *     cacheManager:指定缓存管理器；或者指定cacheResolver缓存解析器
     *     condition：指定符合条件的情况下才缓存；
     *     unless：否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断；
     *     sync：是否使用异步模式
     *
     *原理：
     *     1、自动配置类；CacheAutpConfiguration
     *     org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *     org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *
     *     2、默认生效的配置类：SimpleCacheConfigutaion;ConcurrentMapCacheManager(缓存管理器)
     *     3、可以获取和创建ConcurrentMapCache类型的缓存组件，将数据保存到ConcurrentMap中；
     *     4、运行流程
     * @Cacheable：
     *    1、方法运行之前，先查询Cache（缓存组件），按照cacheNames指定的名字获取；
     *    （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建
     *    2、去Cache中查找缓存的内容，使用一个key，默认使用方法的参数；
     *    key是按照某种策略生成出来的：默认使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
     *        SimpleKeyGenerator生成key的策略：
     *            如果没有参数：key=new SimpleKey()；
     *            如果有一个参数，key=参数的值
     *            如果有多个参数，key=呢哇SimpleKey(params);
     *
     *    3、没有查到缓存，就调用目标方法；
     *    4、将目标方法返回的结果，放进缓存
     *
     * @Cacheable标注的方法执行之前先来检查缓存中有没有数据，默认按照参数的值作为key去查询缓存，如果没有
     * 就运行方法并将结果放入缓存;以后再来调用就可以直接使用缓存中的数据；
     *
     * 核心：
     *     1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConfurrentMapCache】组件
     *     2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
     *
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp",key = "#id")/*,condition = "#a0>1",unless = "#a0==2")*/
    public Employee getEmp(Integer id){
        System.out.println("select "+id+" employee");
        return employeeMapper.getEmployeeById(id);
    }


    /**
     * @CachePut：即调用方法，又更新缓存；
     * 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     *   1、先调用目标方法
     *   2、将目标方法的结果缓存起来
     *
     *   测试步骤：查询1号员工，更新一号员工
     */
    @CachePut(value="emp",key="#employee.id")
    public Employee updateEmp(Employee employee){
         System.out.println("updateEmp:"+employee);
         employeeMapper.updateEmp(employee);
         return employee;
    }

    /**
     * @CacheEvict:缓存清除
     * key:指定要清除的数据
     * allEntries=true：清空缓存中的数据
     * beforeInvocation=true 代表清除缓存操作实在方法运行之前执行，无论方法是否出现异常，
     * 都清除
     */
    @CacheEvict(value = "emp",key="#id")
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        //employeeMapper.deleteEmp(id);
    }

    /**
     *
     * @param lastName
     * @return
     */

    @Caching(
            cacheable={
                    @Cacheable(value ="emp",key="#lastName")
            },
            //cachePut方法执行后缓存
            put={
                    @CachePut(value ="emp",key="#result.id"),
                    @CachePut(value = "emp",key="#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }
}
