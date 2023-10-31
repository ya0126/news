package com.stu.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.article.dtos.ArticleHomeDto;
import com.stu.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章mapper
 *
 * @author yaoh
 */
@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 文章加载查询
     *
     * @param dto
     * @param type
     * @return List<ApArticle>
     */
    List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") short type);
}
