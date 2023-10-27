package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 频道信息controller
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Autowired
    private WmChannelService wmChannelService;

    /**
     * 条件分页查询
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping("/list")
    public ResponseResult listChannel(@RequestBody WmChannelDto dto) {
        return wmChannelService.listChannel(dto);
    }

    /**
     * 查询所有
     *
     * @return ResponseResult
     */
    @GetMapping("/channels")
    public ResponseResult findAll() {
        return wmChannelService.findAll();
    }

    /**
     * 保存频道
     *
     * @param wmChannel
     * @return ResponseResult
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody WmChannel wmChannel) {
        return wmChannelService.saveChannel(wmChannel);
    }

    /**
     * 修改
     *
     * @param wmChannel
     * @return ResponseResult
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmChannel wmChannel) {
        return wmChannelService.updateChannel(wmChannel);
    }

    /**
     * 删除
     *
     * @param channelId
     * @return ResponseResult
     */
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer channelId) {
        return wmChannelService.deleteChannel(channelId);
    }

}
