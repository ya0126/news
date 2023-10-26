package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
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
     * 文章审核联合分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult newsAuthPageQuery(NewsAuthDto dto);

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

    /**
     * 文章联合查询
     * @param newsId
     * @return ResponseResult
     */
    ResponseResult getOneVo(Integer newsId);

    /**
     * 审核未通过
     * @param dto
     * @return ResponseResult
     */
    ResponseResult authFail(NewsAuthDto dto);

    /**
     * 审核通过
     * @param dto
     * @return ResponseResult
     */
    ResponseResult authPass(NewsAuthDto dto);
}
