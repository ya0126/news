package com.stu.wemedia.controller.v1;

import com.stu.common.constants.WemediaConstants;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.NewsAuthDto;
import com.stu.model.wemedia.dtos.WmNewsDto;
import com.stu.model.wemedia.dtos.WmNewsPageReqDto;
import com.stu.wemedia.service.WmNewsAuthService;
import com.stu.wemedia.service.WmNewsService;
import org.springframework.web.bind.annotation.*;

/**
 * 自媒体图文引用素材信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    private final WmNewsService wmNewsService;
    private final WmNewsAuthService wmNewsAuthService;

    public WmNewsController(WmNewsService wmNewsService, WmNewsAuthService wmNewsAuthService) {
        this.wmNewsService = wmNewsService;
        this.wmNewsAuthService = wmNewsAuthService;
    }

    /**
     * 查询所有文章
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto) {
        return wmNewsService.findAll(dto);
    }

    /**
     * 文章列表联合查询(作者信息)
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list_vo")
    public ResponseResult listVo(@RequestBody NewsAuthDto dto) {
        return wmNewsAuthService.listVo(dto);
    }

    /**
     * 文章联合查询(作者信息)
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult getOneVo(@PathVariable("id") Integer newsId) {
        return wmNewsAuthService.getOneVo(newsId);
    }

    /**
     * 查询文章
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/one/{newsId}")
    public ResponseResult getOne(@PathVariable("newsId") Integer newsId) {
        return ResponseResult.okResult(wmNewsService.getById(newsId));
    }

    /**
     * 删除文章
     *
     * @param newsId
     * @return ResponseResult
     */
    @GetMapping("/del_news/{newsId}")
    public ResponseResult delete(@PathVariable("newsId") Integer newsId) {
        return wmNewsService.delete(newsId);
    }

    /**
     * 提交文章
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/submit")
    public ResponseResult submit(@RequestBody WmNewsDto dto) {
        return wmNewsService.submit(dto);
    }

    /**
     * 文章上、下架
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
        return wmNewsAuthService.updateAuthStatus(dto, WemediaConstants.WM_NEWS_AUTH_FAIL);
    }

    /**
     * 审核通过
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto) {
        return wmNewsAuthService.updateAuthStatus(dto, WemediaConstants.WM_NEWS_AUTH_FAIL);
    }
}
