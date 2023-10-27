package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.ApCollectionService;
import com.heima.model.article.pojos.ApCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章收藏信息业务层service实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApCollectionServiceImpl extends ServiceImpl<ApCollectionMapper, ApCollection> implements ApCollectionService {
}
