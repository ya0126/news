package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * app用户认证dto
 *
 * @author yaoh
 */
@Data
public class ApUserAuthDto extends PageRequestDto {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 审核消息
     */
    private String message;

    /**
     * 认证状态
     */
    private Short status;
}
