package com.heima.behavior.controller.v1;

import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户点赞信息controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/like_behavior")
public class ApLikesBehaviorController {

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    @PostMapping
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return apLikesBehaviorService.like(dto);
    }
}
