package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
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
