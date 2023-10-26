package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;

/**
 * 频道信息业务层service
 *
 * @author yaoh
 */
public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     *
     * @return ResponseResult
     */
    ResponseResult findAll();

    /**
     * 保存频道
     *
     * @param wmChannel
     * @return
     */
    ResponseResult saveChannel(WmChannel wmChannel);

    /**
     * 条件分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult listChannel(WmChannelDto dto);

    /**
     * 修改频道
     *
     * @param wmChannel
     * @return ResponseResult
     */
    ResponseResult updateChannel(WmChannel wmChannel);

    /**
     * 删除频道
     *
     * @param channelId
     * @return ResponseResult
     */
    ResponseResult deleteChannel(Integer channelId);
}
