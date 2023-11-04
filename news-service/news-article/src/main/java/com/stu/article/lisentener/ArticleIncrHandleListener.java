package com.stu.article.lisentener;

import com.alibaba.fastjson.JSON;
import com.stu.article.service.ApArticleService;
import com.stu.common.constants.HotArticleConstants;
import com.stu.model.article.mess.ArticleVisitStreamMess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleIncrHandleListener {

    private final ApArticleService apArticleService;

    public ArticleIncrHandleListener(ApArticleService apArticleService) {
        this.apArticleService = apArticleService;
    }

    @KafkaListener(topics = HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC)
    public void onMessage(String mess) {
        if (StringUtils.isNotBlank(mess)) {
            ArticleVisitStreamMess articleVisitStreamMess = JSON.parseObject(mess, ArticleVisitStreamMess.class);
            apArticleService.updateScore(articleVisitStreamMess);
            log.info("接收到消息，{}", articleVisitStreamMess);
        }
    }
}
