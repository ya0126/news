package com.stu.wemedia.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmSensitiveDto;
import com.stu.model.wemedia.pojos.WmSensitive;
import com.stu.wemedia.service.WmSensitivesService;
import org.springframework.web.bind.annotation.*;

/**
 * 自媒体关键词接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitivesController {

    private final WmSensitivesService wmSensitivesService;

    public WmSensitivesController(WmSensitivesService wmSensitivesService) {
        this.wmSensitivesService = wmSensitivesService;
    }

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
