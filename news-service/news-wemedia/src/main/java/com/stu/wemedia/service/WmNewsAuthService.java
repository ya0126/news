package com.stu.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.NewsAuthDto;
import com.stu.model.wemedia.pojos.WmNews;

/**
 * 文章审核业务层
 *
 * @author yaoh
 */
public interface WmNewsAuthService extends IService<WmNews> {

    /**
     * 文章列表联合查询(作者信息)
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult listVo(NewsAuthDto dto);

    /**
     * 文章联合查询(作者信息)
     *
     * @param newsId
     * @return ResponseResult
     */
    ResponseResult getOneVo(Integer newsId);

    /**
     * 修改审核状态
     *
     * @param dto
     * @param status
     * @return ResponseResult
     */
    ResponseResult updateAuthStatus(NewsAuthDto dto, Short status);
}
