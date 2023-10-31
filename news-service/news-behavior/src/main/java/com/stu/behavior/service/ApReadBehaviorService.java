package com.stu.behavior.service;


import com.stu.model.behavior.dtos.ReadBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;

public interface ApReadBehaviorService {

    /**
     * 保存阅读行为
     *
     * @param dto
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}
