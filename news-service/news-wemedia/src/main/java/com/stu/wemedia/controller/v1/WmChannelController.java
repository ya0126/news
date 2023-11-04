package com.stu.wemedia.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.dtos.WmChannelDto;
import com.stu.model.wemedia.pojos.WmChannel;
import com.stu.wemedia.service.WmChannelService;
import org.springframework.web.bind.annotation.*;

/**
 * 频道信息接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    private final WmChannelService wmChannelService;

    public WmChannelController(WmChannelService wmChannelService) {
        this.wmChannelService = wmChannelService;
    }

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
     * 修改频道
     *
     * @param wmChannel
     * @return ResponseResult
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmChannel wmChannel) {
        return wmChannelService.updateChannel(wmChannel);
    }

    /**
     * 删除频道
     *
     * @param channelId
     * @return ResponseResult
     */
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer channelId) {
        return wmChannelService.deleteChannel(channelId);
    }

}
