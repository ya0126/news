package com.stu.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmLoginDto;
import com.stu.model.wemedia.pojos.WmUser;


/**
 * 自媒体用户业务层
 */
public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体端登录
     *
     * @param dto
     * @return
     */
    ResponseResult login(WmLoginDto dto);
}