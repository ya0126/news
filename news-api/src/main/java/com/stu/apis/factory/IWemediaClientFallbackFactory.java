package com.stu.apis.factory;

import com.stu.apis.client.IWemediaClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.wemedia.pojos.WmUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IWemediaClientFallbackFactory implements FallbackFactory<IWemediaClient> {
    @Override
    public IWemediaClient create(Throwable throwable) {
        return new IWemediaClient() {
            @Override
            public WmUser findWmUserByName(String name) {
                log.error("IWemediaClient调用异常，根据姓名查找用户失败", throwable);
                return null;
            }

            @Override
            public ResponseResult saveWmUser(WmUser wmUser) {
                log.error("IWemediaClient调用异常，保存自媒体端用户失败", throwable);
                return null;
            }

            @Override
            public ResponseResult getChannels() {
                log.error("IWemediaClient调用异常，获取频道信息失败", throwable);
                return null;
            }
        };
    }
}
