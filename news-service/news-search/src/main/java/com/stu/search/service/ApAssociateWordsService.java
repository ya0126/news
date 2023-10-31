package com.stu.search.service;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.UserSearchDto;

/**
 * 联想词业务层
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
