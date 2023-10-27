package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.CollectionBehaviorService;
import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApCollection;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 文章收藏业务层service
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class CollectionBehaviorServiceImpl implements CollectionBehaviorService {

    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 用户收藏、取消收藏文章
     *
     * @param dto
     * @return ResponseResult
     */
    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto) {
        // 1.参数校验
        if (dto == null || dto.getEntryId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        Integer userid = AppThreadLocalUtil.getUser().getId();
        // 2.操作数据
        if (dto.getOperation()) {
            LambdaQueryWrapper<ApCollection> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ApCollection::getEntryId, userid).eq(ApCollection::getArticleId, dto.getEntryId());
            apCollectionMapper.delete(wrapper);
        } else {
            ApCollection apCollection = new ApCollection();
            apCollection.setEntryId(userid);
            apCollection.setArticleId(dto.getEntryId());
            apCollection.setType(dto.getType());
            apCollection.setCollectionTime(new Date());

            ApArticle apArticle = apArticleMapper.selectById(dto.getEntryId());
            if (apArticle != null) {
                apCollection.setPublishedTime(apArticle.getPublishTime());
            }
            apCollectionMapper.insert(apCollection);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
