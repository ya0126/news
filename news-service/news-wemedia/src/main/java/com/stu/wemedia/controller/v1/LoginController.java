package com.stu.wemedia.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmLoginDto;
import com.stu.wemedia.service.WmUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private final WmUserService wmUserService;

    public LoginController(WmUserService wmUserService) {
        this.wmUserService = wmUserService;
    }

    /**
     * 登录
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/in")
    public ResponseResult login(@RequestBody WmLoginDto dto) {
        return wmUserService.login(dto);
    }
}
