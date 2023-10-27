package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.pojos.ApCollection;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章收藏信息mapper
 *
 * @author yaoh
 */
@Mapper
public interface ApCollectionMapper extends BaseMapper<ApCollection> {
}
