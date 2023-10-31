package com.stu.wemedia.controller.v1;

import com.stu.common.constants.WemediaConstants;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.NewsAuthDto;
import com.stu.model.wemedia.dtos.WmNewsDto;
import com.stu.model.wemedia.dtos.WmNewsPageReqDto;
import com.stu.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 自媒体图文引用素材信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 文章列表查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto) {
        return wmNewsService.findAll(dto);
    }

    /**
     * 文章审核联合分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list_vo")
    public ResponseResult listVo(@RequestBody NewsAuthDto dto) {
        return wmNewsService.newsAuthPageQuery(dto);
    }

    /**
     * 文章联合查询
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult getOneVo(@PathVariable("id") Integer newsId) {
        return wmNewsService.getOneVo(newsId);
    }


    /**
     * 根据id获取文章
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/one/{newsId}")
    public ResponseResult getOne(@PathVariable("newsId") Integer newsId) {
        return ResponseResult.okResult(wmNewsService.getById(newsId));
    }

    /**
     * 根据id删除文章
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/del_news/{newsId}")
    public ResponseResult deleteNews(@PathVariable("newsId") Integer newsId) {
        return wmNewsService.deleteNewsById(newsId);
    }

    /**
     * 提交文章
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.submitNews(dto);
    }

    /**
     * 文章上下架
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }

    /**
     * 审核未通过
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateStatus(dto, WemediaConstants.WM_NEWS_AUTH_FAIL);
    }

    /**
     * 审核通过
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateStatus(dto, WemediaConstants.WM_NEWS_AUTH_FAIL);
    }
}
