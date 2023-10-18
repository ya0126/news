package com.heima.schedule;

import com.heima.common.redis.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 *
 * @author yaoh
 */
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
}
