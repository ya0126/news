package com.stu.wemedia.controller.v1;

import com.stu.common.constants.WemediaConstants;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmMaterialDto;
import com.stu.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * 列表查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }

    /**
     * 上传
     *
     * @param multipartFile
     * @return ResponseResult
     */
    @PostMapping("/upload_picture")
    public ResponseResult upload(MultipartFile multipartFile) {
        return wmMaterialService.upload(multipartFile);
    }

    /**
     * 删除
     *
     * @param materialId
     * @return ResponseResult
     */
    @GetMapping("/del_picture/{materialId}")
    public ResponseResult delete(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.deletePicture(materialId);
    }

    /**
     * 收藏
     *
     * @return ResponseResult
     */
    @GetMapping("/collect/{materialId}")
    public ResponseResult collect(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.collection(materialId, WemediaConstants.COLLECT_MATERIAL);
    }

    /**
     * 取消收藏
     *
     * @return ResponseResult
     */
    @GetMapping("/cancel_collect/{materialId}")
    public ResponseResult cancelCollect(@PathVariable("materialId") Integer materialId) {
        return wmMaterialService.collection(materialId, WemediaConstants.CANCEL_COLLECT_MATERIAL);
    }
}
