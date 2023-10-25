package com.heima.admin.controller;

import com.heima.admin.service.AdLoginService;
import com.heima.model.admin.dtos.AdLoginDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台登录controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/login")
public class AdLoginController {

    @Autowired
    private AdLoginService adLoginService;
    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdLoginDto dto) {
        return adLoginService.login(dto);
    }
}
