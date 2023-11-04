package com.stu.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.common.constants.WemediaConstants;
import com.stu.model.common.dtos.PageResponseResult;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.wemedia.dtos.NewsAuthDto;
import com.stu.model.wemedia.pojos.WmNews;
import com.stu.model.wemedia.vos.NewAuthVo;
import com.stu.wemedia.mapper.WmNewsMapper;
import com.stu.wemedia.service.WmNewsAuthService;
import com.stu.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章审核业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmNewsAuthServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsAuthService {

    private final WmNewsMapper wmNewsMapper;
    private final WmNewsAutoScanService wmNewsAutoScanService;

    public WmNewsAuthServiceImpl(WmNewsMapper wmNewsMapper,
                                 WmNewsAutoScanService wmNewsAutoScanService) {
        this.wmNewsMapper = wmNewsMapper;
        this.wmNewsAutoScanService = wmNewsAutoScanService;
    }

    @Override
    public ResponseResult newsAuthPageQuery(NewsAuthDto dto) {
        // 1.参数检查
        dto.checkParam();

        // 2.记录当前页
        Integer currentPage = dto.getPage();
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        // 3.分页查询
        List<NewAuthVo> newAuthVos = wmNewsMapper.newsAuthList(dto);
        // 4.count查询
        int count = wmNewsMapper.findListCount(dto);

        // 5.结果返回
        ResponseResult responseResult = new PageResponseResult(currentPage, dto.getSize(), count);
        responseResult.setData(newAuthVos);
        return ResponseResult.okResult(responseResult);
    }

    @Override
    public ResponseResult getOneVo(Integer newsId) {
        // 1. 校验参数
        if (newsId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        // 2. 查询
        NewAuthVo newAuthVo = wmNewsMapper.getOneVo(newsId);
        return ResponseResult.okResult(newAuthVo);
    }

    @Override
    public ResponseResult updateStatus(NewsAuthDto dto, Short status) {
        //1.检查参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.查询文章信息
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //3.修改文章的状态
        wmNews.setStatus(status);
        if (StringUtils.isNotBlank(dto.getMessage())) {
            wmNews.setReason(dto.getMessage());
        }
        updateById(wmNews);

        //审核成功，则需要创建app端文章数据，并修改自媒体文章
        if (status.equals(WemediaConstants.WM_NEWS_AUTH_PASS)) {
            //创建app端文章数据
            ResponseResult responseResult = wmNewsAutoScanService.saveAppArticle(wmNews);
            if (responseResult.getCode().equals(200)) {
                wmNews.setArticleId((Long) responseResult.getData());
                wmNews.setStatus(WmNews.Status.PUBLISHED.getCode());
                updateById(wmNews);
            }
        }

        //4.返回
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
