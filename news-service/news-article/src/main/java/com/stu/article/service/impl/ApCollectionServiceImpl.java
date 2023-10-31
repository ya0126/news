package com.stu.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.stu.article.service.ApCollectionService;
import com.stu.common.constants.BehaviorConstants;
import com.stu.common.redis.CacheService;
import com.stu.model.article.dtos.CollectionBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.user.pojos.ApUser;
import com.stu.utils.common.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户收藏业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApCollectionServiceImpl implements ApCollectionService {

    @Autowired
    private CacheService cacheService;

    /**
     * 收藏、取消收藏
     *
     * @param dto
     * @return ResponseResult
     */
    @Override
    public ResponseResult collection(CollectionBehaviorDto dto) {
        // 1.参数校验
        if (dto == null || dto.getEntryId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 2.判断登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Integer userId = user.getId();
        // 3.判断是否已经收藏
        String articleId = (String) cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + userId, dto.getEntryId().toString());
        if (articleId != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "已收藏");
        }
        // 4.收藏、取消收藏
        if (dto.getOperation() == 0) {
            log.info("文章收藏，保存key:{},{},{}", dto.getEntryId(), user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hPut(BehaviorConstants.COLLECTION_BEHAVIOR + userId, dto.getEntryId().toString(), JSON.toJSONString(dto));
        } else {
            log.info("文章收藏，删除key:{},{},{}", dto.getEntryId(), user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hDelete(BehaviorConstants.COLLECTION_BEHAVIOR + userId, dto.getEntryId().toString());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
