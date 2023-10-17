package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
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
    public ResponseResult uploadPicture(MultipartFile multipartFile)      {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 删除图片
     * @param materialId
     * @return ResponseResult
     */
    @GetMapping("/del_picture/{materialId}")
    public ResponseResult deletePicture(@PathVariable("materialId") Integer materialId){
        return wmMaterialService.deletePicture(materialId);
    }

    /**
     * 收藏图片
     * @return ResponseResult
     */
    @GetMapping("/collect/{materialId}")
    public ResponseResult collect(@PathVariable("materialId") Integer materialId){
        return wmMaterialService.collect(materialId);
    }

    /**
     * 取消收藏图片
     * @return ResponseResult
     */
    @GetMapping("/cancel_collect/{materialId}")
    public ResponseResult cancelCollect(@PathVariable("materialId") Integer materialId){
        return wmMaterialService.cancelCollect(materialId);
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
