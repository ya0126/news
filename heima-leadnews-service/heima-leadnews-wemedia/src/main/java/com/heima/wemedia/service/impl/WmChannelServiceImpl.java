package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmNewsService;
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
        // 1.校验参数
        if (wmChannel == null || StringUtils.isBlank(wmChannel.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 2.校验频道名字是否重复
        WmChannel one = getOne(Wrappers.<WmChannel>lambdaQuery().eq(WmChannel::getName, wmChannel.getName()));
        if (one != null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "频道名称已经存在");

        // 2.保存频道信息
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
        // 1.校验参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 2.校验分页参数
        dto.checkParam();

        // 3.分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();

        // 3.1 根据name模糊查询
        if (StringUtils.isNotBlank(dto.getName())) {
            queryWrapper.like(WmChannel::getName, dto.getName());
        }

        // 3.2 根据状态查找
        if (dto.getStatus() != null) {
            queryWrapper.eq(WmChannel::getStatus, dto.getStatus());
        }
        // 3.3 根据createdTime降序排序
        queryWrapper.orderByDesc(WmChannel::getCreatedTime);

        page = page(page, queryWrapper);

        // 4.封装返回参数
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
        // 1.参数校验
        if (wmChannel == null || wmChannel.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 2.判断是否被引用
        int count = wmNewsService.count(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, wmChannel.getId())
                .eq(WmNews::getStatus, WmNews.Status.PUBLISHED.getCode()));
        if (count > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道被引用不能修改或禁用");
        }
        // 3.修改
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
        // 1.校验参数
        if (channelId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.查询频道
        WmChannel wmChannel = getById(channelId);
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // 3.判断是否有效
        if (wmChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道有效，无法删除");
        }

        // 4.判断是否被引用
        int count = wmNewsService.count(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, channelId)
                .eq(WmNews::getStatus, WmNews.Status.PUBLISHED.getCode()));
        if (count > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道被引用，无法删除");
        }

        // 5.删除
        removeById(channelId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
