package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.pojos.ApArticleConfig;

import java.util.Map;

/**
 * 文章配置业务层service
 *
 * @author yaoh
 */
public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * 修改文章配置
     *
     * @param map
     */
    void updateByMap(Map map);
}
