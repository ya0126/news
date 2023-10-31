package com.stu.search.service;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.HistorySearchDto;

/**
 * 用户搜索业务层
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
