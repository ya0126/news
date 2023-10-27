package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * 联想词业务层service
 *
 * @author yaoh
 */
public interface ApAssociateWordsService {
    /**
     * 搜索联想词
     *
     * @param userSearchDto
     * @return
     */
    ResponseResult findAssociate(UserSearchDto userSearchDto);
}
