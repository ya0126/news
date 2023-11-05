package com.stu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.admin.dtos.LoginDto;
import com.stu.model.admin.pojos.AdUser;
import com.stu.model.common.dtos.ResponseResult;

/**
 * 登录业务层
 *
 * @author yaoh
 */
public interface LoginService extends IService<AdUser> {

    /**
     * 登录
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult login(LoginDto dto);
}
