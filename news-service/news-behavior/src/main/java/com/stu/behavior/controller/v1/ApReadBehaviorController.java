package com.stu.behavior.controller.v1;

import com.stu.behavior.service.ApReadBehaviorService;
import com.stu.model.behavior.dtos.ReadBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户阅读接口
 */
@RestController
@RequestMapping("/api/v1/read_behavior")
public class ApReadBehaviorController {

    @Autowired
    private ApReadBehaviorService apReadBehaviorService;

    /**
     * 阅读
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto dto) {
        return apReadBehaviorService.readBehavior(dto);
    }
}
