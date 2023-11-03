package com.stu.apis.client.wemedia;

import com.stu.apis.client.wemedia.fallback.IWemediaClientFallbackFactory;
import com.stu.apis.config.DefaultFeignConfiguration;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "WeMedisService",
        configuration = DefaultFeignConfiguration.class,
        fallbackFactory = IWemediaClientFallbackFactory.class)
public interface IWemediaClient {

    @GetMapping("/api/v1/user/findByName/{name}")
    WmUser findWmUserByName(@PathVariable("name") String name);

    @PostMapping("/api/v1/wm_user/save")
    ResponseResult saveWmUser(@RequestBody WmUser wmUser);

    @GetMapping("/api/v1/channel/list")
    public ResponseResult getChannels();
}
