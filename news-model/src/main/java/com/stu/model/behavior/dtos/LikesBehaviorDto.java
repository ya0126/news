package com.stu.model.behavior.dtos;

import lombok.Data;

/**
 * 点赞dto
 *
 * @author yaoh
 */
@Data
public class LikesBehaviorDto {

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 0-点赞
     * 1-取消点赞
     */
    private Short operation;

    /**
     * 0-文章
     * 1-动态
     * 2-评论
     */
    private Short type;
}
