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
     * 文章审核联合分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult newsAuthPageQuery(NewsAuthDto dto);

    /**
     * 文章联合查询
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
    ResponseResult updateStatus(NewsAuthDto dto, Short status);
}
