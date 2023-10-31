package com.stu.user.controller.v1;

import com.stu.common.constants.UserConstants;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.user.dtos.AuthDto;
import com.stu.user.service.ApUserRealNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController {

    @Autowired
    private ApUserRealNameService apUserRealNameService;

    /**
     * 认证列表
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult listAuth(@RequestBody AuthDto dto) {
        return apUserRealNameService.pageQuery(dto);
    }

    /**
     * 审核失败
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/authFail")
    public ResponseResult authFiil(@RequestBody AuthDto dto) {
        return apUserRealNameService.updateStatus(dto, UserConstants.FAIL_AUTH);
    }

    /**
     * 审核成功
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/authPass")
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return apUserRealNameService.updateStatus(dto, UserConstants.PASS_AUTH);
    }
}
