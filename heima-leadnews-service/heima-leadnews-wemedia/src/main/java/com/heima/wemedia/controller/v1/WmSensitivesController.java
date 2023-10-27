package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.service.WmSensitivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 自媒体关键词controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitivesController {

    @Autowired
    private WmSensitivesService wmSensitivesService;

    /**
     * 条件分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult list(@RequestBody WmSensitiveDto dto) {
        return wmSensitivesService.list(dto);
    }

    /**
     * 保存关键词
     *
     * @param wmSensitive
     * @return ResponseResult
     */
    @PostMapping("/save")
    public ResponseResult saveSensitives(@RequestBody WmSensitive wmSensitive) {
        return wmSensitivesService.saveSensitives(wmSensitive);
    }

    /**
     * 修改关键词
     *
     * @param wmSensitive
     * @return ResponseResult
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmSensitive wmSensitive) {
        return wmSensitivesService.updateSensitive(wmSensitive);
    }

    /**
     * 删除关键词
     *
     * @param wmSensitiveId
     * @return ResponseResult
     */
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer wmSensitiveId) {
        return wmSensitivesService.deleteSensitice(wmSensitiveId);
    }
}
