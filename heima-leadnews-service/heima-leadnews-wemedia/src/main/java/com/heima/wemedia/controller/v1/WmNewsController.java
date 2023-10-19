package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
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
     * 列表查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto) {
        return wmNewsService.findAll(dto);
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
     * 提交素材
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
}
