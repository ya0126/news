package com.stu.search.controller.v1;

import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.search.dtos.HistorySearchDto;
import com.stu.search.service.ApUserSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索历史接口
 *
 * @author yaoh
 */
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController {

    private final ApUserSearchService apUserSearchService;

    public ApUserSearchController(ApUserSearchService apUserSearchService) {
        this.apUserSearchService = apUserSearchService;
    }

    /**
     * 加载搜索历史
     *
     * @return ResponseResult
     */
    @PostMapping("/load")
    public ResponseResult findUserSearch() {
        return apUserSearchService.findUserSearch();
    }

    /**
     * 删除搜索历史
     *
     * @param historySearchDto
     * @return ResponseResult
     */
    @PostMapping("/del")
    public ResponseResult delUserSearch(@RequestBody HistorySearchDto historySearchDto) {
        return apUserSearchService.delUserSearch(historySearchDto);
    }
}
