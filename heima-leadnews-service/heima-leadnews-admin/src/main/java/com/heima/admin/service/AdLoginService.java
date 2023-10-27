package com.heima.admin.service;

import com.heima.model.admin.dtos.AdLoginDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 后台系统登录业务层service
 *
 * @author yaoh
 */
public interface AdLoginService {

    /**
     * 后台登录
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult login(AdLoginDto dto);
}
