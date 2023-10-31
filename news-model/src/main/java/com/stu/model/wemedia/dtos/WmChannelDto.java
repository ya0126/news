package com.stu.model.wemedia.dtos;

import com.stu.model.common.dtos.PageRequestDto;
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
