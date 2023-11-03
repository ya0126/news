package com.stu.apis.client.schedule.fallback;

import com.stu.apis.client.schedule.IScheduleClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.schedule.dtos.Task;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IScheduleClientFallbackFactory implements FallbackFactory<IScheduleClient> {
    @Override
    public IScheduleClient create(Throwable throwable) {
        return new IScheduleClient() {
            @Override
            public ResponseResult addTask(Task task) {
                log.error("IScheduleClient调用异常，添加任务失败", throwable);
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
            }

            @Override
            public ResponseResult cancelTask(long taskId) {
                log.error("IScheduleClient调用异常，取消任务失败", throwable);
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
            }

            @Override
            public ResponseResult poll(int type, int priority) {
                log.error("IScheduleClient调用异常，拉取任务失败", throwable);
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
            }
        };
    }
}
