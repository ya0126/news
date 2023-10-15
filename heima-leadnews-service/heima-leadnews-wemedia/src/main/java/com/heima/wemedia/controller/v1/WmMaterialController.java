package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体图文素材信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    private WmMaterialService wmMaterialService;

    public WmMaterialController(WmMaterialService wmMaterialService) {
        this.wmMaterialService = wmMaterialService;
    }

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return ResponseResult
     */
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 素材列表查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }
}
