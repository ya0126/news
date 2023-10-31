package com.stu.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.article.dtos.ArticleDto;
import com.stu.model.article.dtos.ArticleHomeDto;
import com.stu.model.article.mess.ArticleVisitStreamMess;
import com.stu.model.article.pojos.ApArticle;
import com.stu.model.common.dtos.ResponseResult;

/**
 * 文章业务层
 *
 * @author yaoh
 */
public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载文章列表
     *
     * @param loadType 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    ResponseResult load(ArticleHomeDto dto, Short loadType);

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult saveArticle(ArticleDto dto);

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     *
     * @param mess
     */
    void updateScore(ArticleVisitStreamMess mess);
}
