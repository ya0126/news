package com.stu.search.service;


import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.UserSearchDto;

import java.io.IOException;

/**
 * 文章搜索业务层
 *
 * @author yaoh
 */
public interface ApArticleSearchService {

    /**
     * ES文章分页搜索
     *
     * @return
     */
    ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}
