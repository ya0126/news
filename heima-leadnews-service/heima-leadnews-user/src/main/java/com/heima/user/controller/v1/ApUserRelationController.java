package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/user")
public class ApUserRelationController {

    @Autowired
    private ApUserFollowService apUserFlowerService;

    @PostMapping("/user_follow")
    public ResponseResult userFollow(@RequestBody UserRelationDto dto) {
        return apUserFlowerService.userFollow(dto);
    }
}
