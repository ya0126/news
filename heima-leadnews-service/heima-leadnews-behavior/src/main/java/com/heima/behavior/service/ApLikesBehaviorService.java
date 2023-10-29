package com.heima.behavior.service;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ApLikesBehaviorService {

    /**
     * 点赞、取消点赞
     *
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);
}
