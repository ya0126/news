package com.stu.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmChannelDto;
import com.stu.model.wemedia.pojos.WmChannel;

/**
 * 频道信息业务层
 *
 * @author yaoh
 */
public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有
     *
     * @return ResponseResult
     */
    ResponseResult findAll();

    /**
     * 保存
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
     * 修改
     *
     * @param wmChannel
     * @return ResponseResult
     */
    ResponseResult updateChannel(WmChannel wmChannel);

    /**
     * 删除
     *
     * @param channelId
     * @return ResponseResult
     */
    ResponseResult deleteChannel(Integer channelId);
}
