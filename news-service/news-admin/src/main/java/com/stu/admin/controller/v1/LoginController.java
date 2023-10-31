package com.stu.admin.controller.v1;

import com.stu.admin.service.LoginService;
import com.stu.model.admin.dtos.LoginDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/in")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return loginService.login(dto);
    }
}
