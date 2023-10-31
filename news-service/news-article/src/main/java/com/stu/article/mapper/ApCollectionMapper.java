package com.stu.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.article.pojos.ApCollection;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章收藏mapper
 *
 * @author yaoh
 */
@Mapper
public interface ApCollectionMapper extends BaseMapper<ApCollection> {
}
