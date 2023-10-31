package com.stu.wemedia.service;


import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.pojos.WmNews;

/**
 * 自媒体文章发布自动审核业务层
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

    /**
     * 保存app端相关的文章数据
     *
     * @param wmNews
     * @return ResponseResult
     */
    ResponseResult saveAppArticle(WmNews wmNews);
}
