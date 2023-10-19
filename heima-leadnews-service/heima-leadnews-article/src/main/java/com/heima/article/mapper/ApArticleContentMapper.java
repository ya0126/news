package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.pojos.ApArticleContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章内容mapper
 *
 * @author yaoh
 */
@Mapper
public interface ApArticleContentMapper extends BaseMapper<ApArticleContent> {
}
