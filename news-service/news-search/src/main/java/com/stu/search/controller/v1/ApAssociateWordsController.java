package com.stu.search.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.UserSearchDto;
import com.stu.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 联想词接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/associate")
public class ApAssociateWordsController {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    /**
     * 搜索联想词
     *
     * @param userSearchDto
     * @return ResponseResult
     */
    @PostMapping("/search")
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) {
        return apAssociateWordsService.findAssociate(userSearchDto);
    }
}
