package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 文章服务
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
}
