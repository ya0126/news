package com.stu.search.service.impl;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.search.dtos.UserSearchDto;
import com.stu.search.pojos.ApAssociateWords;
import com.stu.search.service.ApAssociateWordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 联想词业务层实现类
 *
 * @author yaoh
 */
@Service
@Slf4j
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 搜索联想词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAssociate(UserSearchDto dto) {
        // 1.参数校验
        if (dto == null || StringUtils.isBlank(dto.getSearchWords())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 2.联想词搜索：mongo
        Query query = Query.query(Criteria.where("associateWords").regex(".*?\\" + dto.getSearchWords() + ".*"));
        query.limit(dto.getPageSize());
        List<ApAssociateWords> apAssociateWordsList = mongoTemplate.find(query, ApAssociateWords.class);

        return ResponseResult.okResult(apAssociateWordsList);
    }
}
