package com.heima.model.user.dtos;

import lombok.Data;

/**
 * 用户关注dto
 *
 * @author yaoh
 */
@Data
public class UserRelationDto {

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 0-关注
     * 1-取消
     */
    private Boolean operation;
}
