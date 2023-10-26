package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 文章审核dto
 *
 * @author yaoh
 */
@Data
public class NewsAuthDto extends PageRequestDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 审核状态
     */
    private Short status;
    /**
     * 文章标题
     */
    private String title;

    private String message;

}
