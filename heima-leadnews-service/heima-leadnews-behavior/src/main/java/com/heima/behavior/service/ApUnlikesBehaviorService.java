package com.heima.behavior.service;

import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * APP不喜欢行为表 服务类
 *
 * @author itheima
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