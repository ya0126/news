package com.stu.apis.article;

import com.stu.apis.article.fallback.IArticleClientFallback;
import com.stu.model.article.dtos.ArticleDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ArticleService", fallback = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    ResponseResult saveArticle(@RequestBody ArticleDto dto);
}
