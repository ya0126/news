package com.heima.article.controller.v1;

import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/collection_behavior")
public class CollectionBehaviorController {

    @PostMapping
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto dto) {
        return null;
    }
}
