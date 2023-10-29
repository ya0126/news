package com.heima.admin.controller.v1;

import com.heima.admin.service.LoginService;
import com.heima.model.admin.dtos.LoginDto;
import com.heima.model.common.dtos.ResponseResult;
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
