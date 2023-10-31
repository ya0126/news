package com.stu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.user.dtos.LoginDto;
import com.stu.model.user.pojos.ApUser;

/**
 * apUser业务层service
 *
 * @author yaoh
 */
public interface ApUserService extends IService<ApUser> {

    /**
     * 登录
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult login(LoginDto dto);
}
