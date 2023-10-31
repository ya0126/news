package com.stu.search.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.UserSearchDto;
import com.stu.search.service.ApArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 文章搜索接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController {

    @Autowired
    private ApArticleSearchService apArticleSearchService;

    /**
     * 搜索
     *
     * @param dto
     * @return ResponseResult
     * @throws IOException
     */
    @PostMapping("/search")
    public ResponseResult search(@RequestBody UserSearchDto dto) throws IOException {
        System.err.println(dto);
        return apArticleSearchService.search(dto);
    }
}