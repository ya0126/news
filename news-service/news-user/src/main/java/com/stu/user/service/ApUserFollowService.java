package com.stu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.user.dtos.UserRelationDto;
import com.stu.model.user.pojos.ApUserFollow;

/**
 * 用户关注业务层service
 *
 * @author yaoh
 */
public interface ApUserFollowService extends IService<ApUserFollow> {

    /**
     * 用户关注、取消关注
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult userFollow(UserRelationDto dto);
}
