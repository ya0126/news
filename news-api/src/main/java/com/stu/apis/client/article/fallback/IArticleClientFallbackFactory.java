package com.stu.apis.client.article.fallback;

import com.stu.apis.client.article.IArticleClient;
import com.stu.model.article.dtos.ArticleDto;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IArticleClientFallbackFactory implements FallbackFactory<IArticleClient> {
    @Override
    public IArticleClient create(Throwable throwable) {
        return new IArticleClient() {
            @Override
            public ResponseResult saveArticle(ArticleDto dto) {
                log.error("IArticleClient调用异常，保存文章失败", throwable);
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
            }
        };
    }
}
