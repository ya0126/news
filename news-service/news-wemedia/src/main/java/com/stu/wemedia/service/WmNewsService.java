package com.stu.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmNewsDto;
import com.stu.model.wemedia.dtos.WmNewsPageReqDto;
import com.stu.model.wemedia.pojos.WmNews;


/**
 * 自媒体图文内容信息业务层
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

    /**
     * 文章的上下架
     *
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsDto dto);
}
