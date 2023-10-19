package com.heima.article.service;

import com.heima.model.article.pojos.ApArticle;

/**
 * 文章生成静态文件并上传至minio服务
 *
 * @author yaoh
 */
public interface ArticleFreemarkerService {

    /**
     * 生成静态文件上传到minio
     *
     * @param apArticle
     * @param content
     */
    void buildArticleToMinIO(ApArticle apArticle, String content);
}
