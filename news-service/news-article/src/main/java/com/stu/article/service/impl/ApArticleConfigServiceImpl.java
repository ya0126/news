package com.stu.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.article.mapper.ApArticleConfigMapper;
import com.stu.article.service.ApArticleConfigService;
import com.stu.model.article.pojos.ApArticleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 文章配置业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {

    /**
     * 文章上下线
     *
     * @param map-->enable:0-下线，1上线
     */
    @Override
    public void updateByMap(Map map) {
        boolean isDown = false;
        Object enable = map.get("enable");
        if (enable.equals(0)) {
            isDown = true;
        }
        update(Wrappers.<ApArticleConfig>lambdaUpdate()
                .eq(ApArticleConfig::getArticleId, map.get("articleId"))
                .set(ApArticleConfig::getIsDown, isDown));
    }
}
