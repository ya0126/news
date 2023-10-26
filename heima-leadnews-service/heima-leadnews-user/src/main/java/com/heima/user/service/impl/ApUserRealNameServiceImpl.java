package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserAuthDto;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.user.mapper.ApUserRealNameMapper;
import com.heima.user.service.ApUserRealNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户实名业务层service实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApUserRealNameServiceImpl extends ServiceImpl<ApUserRealNameMapper, ApUserRealname> implements ApUserRealNameService {
    /**
     * 认证列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(ApUserAuthDto dto) {
        // 1.参数校验
        dto.checkParam();

        // 2.查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApUserRealname> wrapper = new LambdaQueryWrapper<>();

        // 2.1 根据状态查询
        if (dto.getStatus() != null) {
            wrapper.eq(ApUserRealname::getStatus, dto.getStatus());
        }

        page = page(page, wrapper);
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        pageResponseResult.setData(page.getRecords());
        return ResponseResult.okResult(pageResponseResult);
    }
}
