package com.stu.user.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.user.dtos.LoginDto;
import com.stu.user.service.ApUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController {

    private final ApUserService apUserService;

    public ApUserLoginController(ApUserService apUserService) {
        this.apUserService = apUserService;
    }

    /**
     * 用户登录
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("login_auth")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }
}
