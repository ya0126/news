package com.stu.article.controller.v1;

import com.stu.article.service.ApCollectionService;
import com.stu.model.article.dtos.CollectionBehaviorDto;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ApCollectionController {

    @Autowired
    private ApCollectionService apCollectionService;

    /**
     * 收藏
     *
     * @param dto
     * @return ResponseResult
     */
    @PostMapping
    public ResponseResult collection(@RequestBody CollectionBehaviorDto dto) {
        return apCollectionService.collection(dto);
    }
}
