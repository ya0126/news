package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserAuthDto;
import com.heima.user.service.ApUserRealNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/auth")
public class ApUserAuthController {

    @Autowired
    private ApUserRealNameService apUserRealNameService;

    /**
     * 认证列表
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult listAuth(@RequestBody ApUserAuthDto dto) {
        return apUserRealNameService.pageQuery(dto);
    }
}
