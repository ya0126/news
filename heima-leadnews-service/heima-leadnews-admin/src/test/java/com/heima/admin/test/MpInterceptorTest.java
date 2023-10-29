package com.heima.admin.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.admin.service.UserService;
import com.heima.model.admin.pojo.AdUser;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class MpInterceptorTest {

    @Autowired
    private UserService userService;

    @Test
    public void testPlugin() {
        userService.update(Wrappers.<AdUser>lambdaUpdate().eq(AdUser::getId, 3).set(AdUser::getName, "张三"));
    }

}
