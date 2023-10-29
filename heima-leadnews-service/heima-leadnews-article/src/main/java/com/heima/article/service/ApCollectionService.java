package com.heima.article.service;

import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 收藏业务层
 *
 * @author yaoh
 */
public interface ApCollectionService {

    /**
     * 收藏、取消收藏
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult collection(CollectionBehaviorDto dto);
}
