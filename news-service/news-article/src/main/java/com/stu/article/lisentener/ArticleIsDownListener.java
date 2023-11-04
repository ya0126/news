package com.stu.article.lisentener;

import com.alibaba.fastjson.JSON;
import com.stu.article.service.ApArticleConfigService;
import com.stu.common.constants.WmNewsMessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听文章上下线
 *
 * @author yaoh
 */
@Component
@Slf4j
public class ArticleIsDownListener {

    private final ApArticleConfigService apArticleConfigService;

    public ArticleIsDownListener(ApArticleConfigService apArticleConfigService) {
        this.apArticleConfigService = apArticleConfigService;
    }

    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void onMessage(String message) {
        Map map = JSON.parseObject(message, Map.class);
        log.info("接收到消息,{}", map);
        apArticleConfigService.updateByMap(map);
        log.info("article端文章配置修改，articleId={}", map.get("articleId"));
    }
}
