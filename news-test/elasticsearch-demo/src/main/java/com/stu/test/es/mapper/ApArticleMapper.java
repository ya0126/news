package com.stu.test.es.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.article.pojos.ApArticle;
import com.stu.test.es.pojo.SearchArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    public List<SearchArticleVo> loadArticleList();

}
