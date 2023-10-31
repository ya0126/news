package com.stu.model.wemedia.dtos;

import com.stu.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 自媒体图文素材信息dto
 *
 * @author yaoh
 */
@Data
public class WmMaterialDto extends PageRequestDto {

    /**
     * 1 收藏
     * 0 未收藏
     */
    private Short isCollection;
}
