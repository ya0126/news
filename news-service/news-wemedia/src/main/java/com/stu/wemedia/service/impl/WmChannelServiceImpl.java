package com.stu.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.model.common.dtos.PageResponseResult;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.wemedia.dtos.WmChannelDto;
import com.stu.model.wemedia.pojos.WmChannel;
import com.stu.model.wemedia.pojos.WmNews;
import com.stu.wemedia.mapper.WmChannelMapper;
import com.stu.wemedia.service.WmChannelService;
import com.stu.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 频道信息业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 保存频道
     *
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult saveChannel(WmChannel wmChannel) {
        if (wmChannel == null || StringUtils.isBlank(wmChannel.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmChannel one = getOne(Wrappers.<WmChannel>lambdaQuery().eq(WmChannel::getName, wmChannel.getName()));
        if (one != null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "频道名称已经存在");

        wmChannel.setCreatedTime(new Date());
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 条件分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult listChannel(WmChannelDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        dto.checkParam();

        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(dto.getName())) {
            queryWrapper.like(WmChannel::getName, dto.getName());
        }
        if (dto.getStatus() != null) {
            queryWrapper.eq(WmChannel::getStatus, dto.getStatus());
        }
        queryWrapper.orderByDesc(WmChannel::getCreatedTime);
        page = page(page, queryWrapper);

        ResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        pageResponseResult.setData(page.getRecords());
        return ResponseResult.okResult(pageResponseResult);
    }

    /**
     * 修改频道
     *
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult updateChannel(WmChannel wmChannel) {
        if (wmChannel == null || wmChannel.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 判断是否被引用
        int count = wmNewsService.count(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, wmChannel.getId())
                .eq(WmNews::getStatus, WmNews.Status.PUBLISHED.getCode()));
        if (count > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道被引用不能修改或禁用");
        }
        updateById(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除频道
     *
     * @param channelId
     * @return
     */
    @Override
    public ResponseResult deleteChannel(Integer channelId) {
        if (channelId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        WmChannel wmChannel = getById(channelId);
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        if (wmChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道有效，无法删除");
        }

        // 判断是否被引用
        int count = wmNewsService.count(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, channelId)
                .eq(WmNews::getStatus, WmNews.Status.PUBLISHED.getCode()));
        if (count > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道被引用，无法删除");
        }

        removeById(channelId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
