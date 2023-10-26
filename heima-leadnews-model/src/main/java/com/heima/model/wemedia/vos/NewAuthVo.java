package com.heima.model.wemedia.vos;

import com.heima.model.wemedia.pojos.WmNews;
import lombok.Data;

/**
 * 文章审核vo
 *
 * @author yaoh
 */
@Data
public class NewAuthVo extends WmNews {

    /**
     * 作者名字
     */
    private String authorName;
}
