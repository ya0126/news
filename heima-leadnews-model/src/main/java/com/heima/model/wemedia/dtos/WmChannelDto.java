package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmChannelDto extends PageRequestDto {

    /**
     * 频道名称
     */
    private String name;

    /**
     * 状态
     */
    private Short status;
}