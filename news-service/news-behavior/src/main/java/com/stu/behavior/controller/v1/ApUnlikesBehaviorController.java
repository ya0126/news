package com.stu.behavior.controller.v1;

import com.stu.behavior.service.ApUnlikesBehaviorService;
import com.stu.model.behavior.dtos.UnLikesBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户不喜欢
 */
@RestController
@RequestMapping("/api/v1/un_likes_behavior")
public class ApUnlikesBehaviorController {

    @Autowired
    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    /**
     * 不喜欢记录
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping
    public ResponseResult unLike(@RequestBody UnLikesBehaviorDto dto) {
        return apUnlikesBehaviorService.unLike(dto);
    }
}