package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

/**
 * 自媒体图文内容信息业务层service
 *
 * @author yaoh
 */
public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询所有文章
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     * 发布文章或保存草稿
     *
     * @param dto
     * @return
     */
    ResponseResult submitNews(WmNewsDto dto);

    /**
     * 根据id删除文章
     *
     * @param newsId
     * @return ResponseResult
     */
    ResponseResult deleteNewsById(Integer newsId);
}
