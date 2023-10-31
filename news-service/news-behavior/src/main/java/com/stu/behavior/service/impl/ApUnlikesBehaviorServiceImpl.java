package com.stu.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.stu.behavior.service.ApUnlikesBehaviorService;
import com.stu.common.constants.BehaviorConstants;
import com.stu.common.redis.CacheService;
import com.stu.model.behavior.dtos.UnLikesBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.user.pojos.ApUser;
import com.stu.utils.common.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * APP不喜欢行为业务层实现类
 */
@Service
@Slf4j
public class ApUnlikesBehaviorServiceImpl implements ApUnlikesBehaviorService {

    @Autowired
    private CacheService cacheService;

    @Override
    public ResponseResult unLike(UnLikesBehaviorDto dto) {
        // 1.参数校验
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 2.判断登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 不喜欢，保存数据
        if (dto.getType() == 0) {
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut(BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        } else {
            log.info("删除当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hDelete(BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}