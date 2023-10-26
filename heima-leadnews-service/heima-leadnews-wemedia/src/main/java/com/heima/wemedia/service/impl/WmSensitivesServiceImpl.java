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
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.WmSensitivesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 自媒体关键词业务层service实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmSensitivesServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitivesService {
    /**
     * 保存关键词
     *
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult saveSensitives(WmSensitive wmSensitive) {
        // 1.参数校验
        if (wmSensitive == null || StringUtils.isBlank(wmSensitive.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.判断是否存在
        WmSensitive one = getOne(Wrappers.<WmSensitive>lambdaQuery().eq(WmSensitive::getSensitives, wmSensitive.getSensitives()));
        if (one != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        // 3.保存
        wmSensitive.setCreatedTime(new Date());
        save(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 条件分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(WmSensitiveDto dto) {
        // 1.参数校验
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        dto.checkParam();

        // 2.分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmSensitive> wrapper = new LambdaQueryWrapper<>();

        // 2.1 根据name 模糊查询
        if (StringUtils.isNotBlank(dto.getName())) {
            wrapper.like(WmSensitive::getSensitives, dto.getName());
        }

        // 2.2 根据created_time 倒序排序
        wrapper.orderByDesc(WmSensitive::getCreatedTime);

        page = page(page, wrapper);

        PageResponseResult resultData = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        resultData.setData(page.getRecords());
        return ResponseResult.okResult(resultData);
    }

    /**
     * 修改关键词
     * @param wmSensitive
     * @return
     */
    @Override
    public ResponseResult updateSensitive(WmSensitive wmSensitive) {
        // 1.参数校验
        if (wmSensitive.getId() == null || StringUtils.isBlank(wmSensitive.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.修改
        updateById(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除关键词
     * @param wmSensitiveId
     * @return
     */
    @Override
    public ResponseResult deleteSensitice(Integer wmSensitiveId) {
        // 1.校验参数
        if (wmSensitiveId==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 2.删除
        removeById(wmSensitiveId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
