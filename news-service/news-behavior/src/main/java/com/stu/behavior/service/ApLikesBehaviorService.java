package com.stu.behavior.service;


import com.stu.model.behavior.dtos.LikesBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;

public interface ApLikesBehaviorService {

    /**
     * 点赞、取消点赞
     *
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);
}
