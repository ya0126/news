package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUserRealname;

/**
 * 用户认证业务层service
 *
 * @author yaoh
 */
public interface ApUserRealNameService extends IService<ApUserRealname> {

    /**
     * 认证列表
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult pageQuery(AuthDto dto);

    /**
     * 修改状态
     *
     * @param dto
     * @param status
     * @return ResponseResult
     */
    ResponseResult updateStatus(AuthDto dto, Short status);
}
