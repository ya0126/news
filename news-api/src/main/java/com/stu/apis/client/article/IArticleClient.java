package com.stu.apis.client.article;

import com.stu.apis.client.article.fallback.IArticleClientFallbackFactory;
import com.stu.apis.config.DefaultFeignConfiguration;
import com.stu.model.article.dtos.ArticleDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "ArticleService",
        configuration = DefaultFeignConfiguration.class,
        fallbackFactory = IArticleClientFallbackFactory.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    ResponseResult saveArticle(@RequestBody ArticleDto dto);
}
