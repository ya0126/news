package com.stu.wemedia.controller.v1;

import com.stu.common.constants.WemediaConstants;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmMaterialDto;
import com.stu.wemedia.service.WmMaterialService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体图文素材信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    private final WmMaterialService wmMaterialService;

    public WmMaterialController(WmMaterialService wmMaterialService) {
        this.wmMaterialService = wmMaterialService;
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

    /**
     * 上传素材
     *
     * @param multipartFile
     * @return ResponseResult
     */
    @PostMapping("/upload_picture")
    public ResponseResult upload(MultipartFile multipartFile) {
        return wmMaterialService.upload(multipartFile);
    }

    /**
     * 删除素材
     *
     * @param materialId
     * @return ResponseResult
     */
    @GetMapping("/del_picture/{materialId}")
    public ResponseResult delete(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.delete(materialId);
    }

    /**
     * 收藏素材
     *
     * @return ResponseResult
     */
    @GetMapping("/collect/{materialId}")
    public ResponseResult collect(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.updateCollection(materialId, WemediaConstants.COLLECT_MATERIAL);
    }

    /**
     * 取消收藏素材
     *
     * @return ResponseResult
     */
    @GetMapping("/cancel_collect/{materialId}")
    public ResponseResult cancelCollect(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.updateCollection(materialId, WemediaConstants.CANCEL_COLLECT_MATERIAL);
    }
}
