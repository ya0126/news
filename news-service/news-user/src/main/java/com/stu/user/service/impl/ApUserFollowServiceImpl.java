package com.stu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.common.constants.BehaviorConstants;
import com.stu.common.redis.CacheService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.user.dtos.UserRelationDto;
import com.stu.model.user.pojos.ApUser;
import com.stu.model.user.pojos.ApUserFollow;
import com.stu.user.mapper.ApUserFollowMapper;
import com.stu.user.service.ApUserFollowService;
import com.stu.utils.common.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户关注业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    @Autowired
    private CacheService cacheService;

    /**
     * 用户关注、取消关注
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult userFollow(UserRelationDto dto) {
        // 1.参数校验
        if (dto == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.判断是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Integer apUserId = user.getId();

        // 2.关注、取消关注
        Integer followUserId = dto.getAuthorId();
        if (dto.getOperation() == 0) {
            // 将对方写入我的关注中
            cacheService.zAdd(BehaviorConstants.APUSER_FOLLOW_RELATION + apUserId, followUserId.toString(), System.currentTimeMillis());
            // 将我写入对方的粉丝中
            cacheService.zAdd(BehaviorConstants.APUSER_FANS_RELATION + followUserId, apUserId.toString(), System.currentTimeMillis());

        } else {
            cacheService.zRemove(BehaviorConstants.APUSER_FOLLOW_RELATION + apUserId, followUserId.toString());
            cacheService.zRemove(BehaviorConstants.APUSER_FANS_RELATION + followUserId, apUserId.toString());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
