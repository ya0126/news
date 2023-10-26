package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体关键词controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitivesController {

    /**
     * 保存关键词
     *
     * @param wmSensitive
     * @return ResponseResult
     */
    @PostMapping("/save")
    public ResponseResult saveSensitives(@RequestBody WmSensitive wmSensitive) {
        return null;
    }
}
