package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

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
