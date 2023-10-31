package com.stu.model.article.dtos;

import com.stu.model.article.pojos.ApArticle;
import lombok.Data;

/**
 * 文章信息dto
 *
 * @author yaoh
 */
@Data
public class ArticleDto extends ApArticle {

    /**
     * 文章内容
     */
    private String content;
}
