package com.stu.common.constants;

/**
 * 文章相关常量
 *
 * @author yaoh
 */
public class ArticleConstants {
    /**
     * 单页最大加载的数字
     */
    public static final Short MAX_PAGE_SIZE = 50;
    /**
     * 加载更多
     */
    public static final Short LOADTYPE_LOAD_MORE = 1;
    /**
     * 加载最新
     */
    public static final Short LOADTYPE_LOAD_NEW = 2;
    /**
     * 加载全部
     */
    public static final String DEFAULT_TAG = "__all__";
    /**
     * 异步添加文章索引topic
     */
    public static final String ARTICLE_ES_SYNC_TOPIC = "article.es.sync.topic";
    /**
     * 文章热度评分权重：喜欢：3
     */
    public static final Integer HOT_ARTICLE_LIKE_WEIGHT = 3;
    /**
     * 文章热度评分权重：评论：5
     */
    public static final Integer HOT_ARTICLE_COMMENT_WEIGHT = 5;
    /**
     * 文章热度评分权重：收藏：8
     */
    public static final Integer HOT_ARTICLE_COLLECTION_WEIGHT = 8;
    /**
     * 热文章首页Redis Key前缀
     */
    public static final String HOT_ARTICLE_FIRST_PAGE = "hot_article_first_page_";
}
