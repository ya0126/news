package com.heima.article.test;

import com.heima.article.ArticleApplication;
import com.heima.article.service.ApArticleConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

/**
 * TODO
 *
 * @author yaoh
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleConfigTest {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @Test
    public void testUpdate() {
        Map<String, Integer> enable = Collections.singletonMap("enable", 1);
        apArticleConfigService.updateByMap(enable);
    }
}
