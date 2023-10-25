package com.heima.search.service;

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
}
