package com.heima.article.service;

import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 文章收藏业务层service
 *
 * @author yaoh
 */
public interface ApCollectionService {

    /**
     * 用户收藏、取消收藏文章
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult collection(CollectionBehaviorDto dto);
}
