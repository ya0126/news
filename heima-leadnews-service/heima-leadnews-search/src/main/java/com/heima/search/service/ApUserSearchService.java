package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;

/**
 * app端用户搜索业务层service
 *
 * @author yaoh
 */
public interface ApUserSearchService {

    /**
     * 保存用户搜索历史记录
     *
     * @param keyword
     * @param userId
     */
    void insert(String keyword, Integer userId);

    /**
     * 查询搜索历史
     *
     * @return ResponseResult
     */
    ResponseResult findUserSearch();

    /**
     * 删除搜索历史
     *
     * @param historySearchDto
     * @return ResponseResult
     */
    ResponseResult delUserSearch(HistorySearchDto historySearchDto);
}
