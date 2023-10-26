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
     * 认证状态
     */
    private Short status;
}
