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
import com.stu.model.wemedia.dtos.WmSensitiveDto;
import com.stu.model.wemedia.pojos.WmSensitive;
import com.stu.wemedia.mapper.WmSensitiveMapper;
import com.stu.wemedia.service.WmSensitivesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 自媒体关键词业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmSensitivesServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitivesService {

    /**
     * 条件分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult list(WmSensitiveDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmSensitive> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())) {
            wrapper.like(WmSensitive::getSensitives, dto.getName());
        }
        wrapper.orderByDesc(WmSensitive::getCreatedTime);
        page = page(page, wrapper);
        ResponseResult resultData = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        resultData.setData(page.getRecords());
        return ResponseResult.okResult(resultData);
    }

    /**
     * 保存关键词
     *
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult saveSensitives(WmSensitive wmSensitive) {
        if (wmSensitive == null || StringUtils.isBlank(wmSensitive.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        WmSensitive one = getOne(Wrappers.<WmSensitive>lambdaQuery().eq(WmSensitive::getSensitives, wmSensitive.getSensitives()));
        if (one != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "敏感词已经存在");
        }
        wmSensitive.setCreatedTime(new Date());
        save(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 修改关键词
     *
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult updateSensitive(WmSensitive wmSensitive) {
        if (wmSensitive.getId() == null || StringUtils.isBlank(wmSensitive.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        updateById(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除关键词
     *
     * @param wmSensitiveId
     * @return
     */
    @Override
    public ResponseResult deleteSensitice(Integer wmSensitiveId) {
        if (wmSensitiveId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        WmSensitive wmSensitive = getById(wmSensitiveId);
        if (wmSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        removeById(wmSensitiveId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
