package com.stu.article.controller.v1;

import com.stu.article.service.ApArticleService;
import com.stu.common.constants.ArticleConstants;
import com.stu.model.article.dtos.ArticleHomeDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    private final ApArticleService apArticleService;

    public ArticleHomeController(ApArticleService apArticleService) {
        this.apArticleService = apArticleService;
    }

    /**
     * 默认加载
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载更多
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/loadmore")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载最新
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/loadnew")
    public ResponseResult loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_NEW);
    }
}
