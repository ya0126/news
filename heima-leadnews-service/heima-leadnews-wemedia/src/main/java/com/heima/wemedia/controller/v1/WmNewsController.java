package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体图文引用素材信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    private WmNewsService wmNewsService;

    public WmNewsController(WmNewsService wmNewsService) {
        this.wmNewsService = wmNewsService;
    }

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
     * 提交素材
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getChannelId() != null) {
            return wmNewsService.updateNews(dto);
        }
        return wmNewsService.addNews(dto);
    }
}
