package com.stu.wemedia.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stu.apis.wemedia.IWemediaClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.wemedia.pojos.WmUser;
import com.stu.wemedia.service.WmChannelService;
import com.stu.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WemediaClient implements IWemediaClient {

    @Autowired
    private WmUserService wmUserService;
    @Autowired
    private WmChannelService wmChannelService;

    @Override
    @GetMapping("/api/v1/user/findByName/{name}")
    public WmUser findWmUserByName(@PathVariable String name) {
        return wmUserService.getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, name));
    }

    @Override
    @PostMapping("/api/v1/wm_user/save")
    public ResponseResult saveWmUser(WmUser wmUser) {
        wmUserService.save(wmUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @GetMapping("/api/v1/channel/list")
    public ResponseResult getChannels() {
        return wmChannelService.findAll();
    }
}
