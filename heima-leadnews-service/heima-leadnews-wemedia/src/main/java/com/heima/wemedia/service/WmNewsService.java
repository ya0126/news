package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

/**
 * TODO
 *
 * @author yaoh
 */
public interface WmNewsService extends IService<WmNews> {

    ResponseResult findAll(WmNewsPageReqDto dto);

    ResponseResult addNews(WmNewsDto dto);

    ResponseResult updateNews(WmNewsDto dto);
}
