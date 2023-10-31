package com.stu.behavior.service;


import com.stu.model.behavior.dtos.UnLikesBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;

/**
 * APP不喜欢业务层
 */
public interface ApUnlikesBehaviorService {

    /**
     * 不喜欢
     *
     * @param dto
     * @return
     */
    public ResponseResult unLike(UnLikesBehaviorDto dto);

}