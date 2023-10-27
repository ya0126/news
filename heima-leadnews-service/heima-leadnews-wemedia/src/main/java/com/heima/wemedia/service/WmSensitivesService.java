package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;

/**
 * 自媒体关键词业务层service
 *
 * @author yaoh
 */
public interface WmSensitivesService extends IService<WmSensitive> {

    /**
     * 条件分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult list(WmSensitiveDto dto);

    /**
     * 保存关键词
     *
     * @param wmSensitive
     * @return ResponseResult
     */
    ResponseResult saveSensitives(WmSensitive wmSensitive);

    /**
     * 修改关键词
     *
     * @param wmSensitive
     * @return ResponseResult
     */
    ResponseResult updateSensitive(WmSensitive wmSensitive);

    /**
     * 删除关键词
     *
     * @param wmSensitiveId
     * @return ResponseResult
     */
    ResponseResult deleteSensitice(Integer wmSensitiveId);
}
