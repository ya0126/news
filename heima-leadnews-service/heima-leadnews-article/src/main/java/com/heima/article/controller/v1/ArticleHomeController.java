package com.heima.article.controller.v1;

import com.heima.article.service.ApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * TODO
 *
 * @author yaoh
 */
@Api(value = "app获取文章接口", tags = "ap_article", description = "app端获取文章API")
@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    private final ApArticleService apArticleService;

    private final FileStorageService fileStorageService;

    public ArticleHomeController(ApArticleService apArticleService, FileStorageService fileStorageService) {
        this.apArticleService = apArticleService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/load")
    @ApiOperation("默认加载")
    public ResponseResult load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadmore")
    @ApiOperation("加载更多")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadnew")
    @ApiOperation("加载最新")
    public ResponseResult loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_NEW);
    }
}
