package com.heima.schedule;

import com.alibaba.fastjson.JSON;
import com.heima.common.redis.CacheService;
import com.heima.model.schedule.dtos.Task;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
public class ScheduleTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void testList(){
        cacheService.lLeftPush("testKey1","testValue1");
        cacheService.lLeftPush("testKey1","testValue2");
        cacheService.lLeftPop("testKey1");
    }

    @Test
    public void testZset(){
        cacheService.zAdd("testKey2","value1",1.0);
        cacheService.zAdd("testKey2","value2",2.0);
        cacheService.zAdd("testKey2","value3",3.0);
    }

    @Test
    public void testKeys(){
        Set<String> keys = cacheService.keys("*");
        System.out.println("keys"+keys);
        Set<String> scan = cacheService.scan("*");
        System.out.println("scan"+scan);
    }


    //耗时6151
    @Test
    public  void testPiple1(){
        long start =System.currentTimeMillis();
        for (int i = 0; i <3000 ; i++) {
            Task task = new Task();
            task.setTaskType(1001);
            task.setPriority(1);
            task.setExecuteTime(new Date().getTime());
            cacheService.lLeftPush("1001_1", JSON.toJSONString(task));
        }
        System.out.println("未使用使用管道技术执行3000次自增操作共耗时"+(System.currentTimeMillis()- start));
    }


    @Test
    public void testPiple2(){
        long start  = System.currentTimeMillis();
        //使用管道技术
        List<Object> objectList = cacheService.getstringRedisTemplate().executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i <3000 ; i++) {
                    Task task = new Task();
                    task.setTaskType(1001);
                    task.setPriority(1);
                    task.setExecuteTime(new Date().getTime());
                    redisConnection.lPush("1001_2".getBytes(), JSON.toJSONString(task).getBytes());
                }
                return null;
            }
        });
        System.out.println("使用管道技术执行3000次自增操作共耗时:"+(System.currentTimeMillis()-start)+"毫秒");
    }

}
