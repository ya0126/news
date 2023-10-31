package com.stu.article.feign;

import com.stu.apis.article.IArticleClient;
import com.stu.article.service.ApArticleService;
import com.stu.model.article.dtos.ArticleDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 保存文章
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }
}