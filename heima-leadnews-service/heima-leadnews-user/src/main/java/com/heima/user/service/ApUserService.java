package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;

/**
 * apUser业务层service
 *
 * @author yaoh
 */
public interface ApUserService extends IService<ApUser> {

    /**
     * app端登录
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult login(LoginDto dto);
}
