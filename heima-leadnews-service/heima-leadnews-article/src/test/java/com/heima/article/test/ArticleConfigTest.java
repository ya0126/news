package com.heima.article.test;

import com.heima.article.ArticleApplication;
import com.heima.article.service.ApArticleConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

/**
 * 文章配置设置
 *
 * @author yaoh
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleConfigTest {

    @Autowired
    private ApArticleConfigService apArticleConfigService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testUpdate() {
        Map<String, Integer> enable = Collections.singletonMap("enable", 1);
        apArticleConfigService.updateByMap(enable);
    }

    @Test
    public void kafka() {
    }
}
