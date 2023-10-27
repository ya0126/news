package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.mapper.ApUserFollowMapper;
import com.heima.user.service.ApUserFollowService;
import com.heima.utils.common.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户关注业务层service实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    /**
     * 用户关注、取消关注
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult userFollow(UserRelationDto dto) {
        // 1.参数校验
        if (dto == null || dto.getAuthorId() == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        Integer userId = user.getId();

        // 2.关注、取消关注
        if (dto.getOperation()) {
            ApUserFollow apUserFollow = new ApUserFollow(userId, dto.getAuthorId());
            save(apUserFollow);
        } else {
            remove(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, userId).eq(ApUserFollow::getFlowerId, dto.getAuthorId()));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
