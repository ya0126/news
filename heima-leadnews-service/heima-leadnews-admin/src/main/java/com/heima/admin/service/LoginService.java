package com.heima.admin.service;

import com.heima.model.admin.dtos.LoginDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 登录业务层service
 *
 * @author yaoh
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult login(LoginDto dto);
}
