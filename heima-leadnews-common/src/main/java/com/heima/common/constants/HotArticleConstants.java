package com.heima.common.constants;

/**
 * 文章热度
 *
 * @author yaoh
 */
public class HotArticleConstants {
    /**
     * 文章热度kafka订阅主题
     */
    public static final String HOT_ARTICLE_SCORE_TOPIC = "hot.article.score.topic";

    /**
     * 文章热度kafka订阅主题聚合处理
     */
    public static final String HOT_ARTICLE_INCR_HANDLE_TOPIC = "hot.article.incr.handle.topic";
}
