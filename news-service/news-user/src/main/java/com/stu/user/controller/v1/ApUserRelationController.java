package com.stu.user.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.user.dtos.UserRelationDto;
import com.stu.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/user")
public class ApUserRelationController {

    @Autowired
    private ApUserFollowService apUserFlowerService;

    /**
     * 用户关注
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/user_follow")
    public ResponseResult userFollow(@RequestBody UserRelationDto dto) {
        return apUserFlowerService.userFollow(dto);
    }
}
