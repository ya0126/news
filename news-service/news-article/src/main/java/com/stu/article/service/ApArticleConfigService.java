package com.stu.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.model.article.pojos.ApArticleConfig;

import java.util.Map;

/**
 * 文章配置业务层
 *
 * @author yaoh
 */
public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * 文章上下线
     *
     * @param map
     */
    void updateByMap(Map map);
}
