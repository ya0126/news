package com.stu.model.wemedia.dtos;

import com.stu.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 自媒体关键词dto
 *
 * @author yaoh
 */
@Data
public class WmSensitiveDto extends PageRequestDto {

    /**
     * 关键词
     */
    private String name;
}
