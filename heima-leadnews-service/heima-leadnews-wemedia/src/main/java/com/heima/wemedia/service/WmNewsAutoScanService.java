package com.heima.wemedia.service;

/**
 * 自媒体文章发布自动审核业务层service
 *
 * @author yaoh
 */
public interface WmNewsAutoScanService {

    /**
     * 自媒体文章审核
     *
     * @param id 自媒体文章id
     */
    void autoScanWmNews(Integer id);
}
