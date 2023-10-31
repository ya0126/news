package com.stu.apis.schedule.fallback;

import com.stu.apis.schedule.IScheduleClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.schedule.dtos.Task;
import org.springframework.stereotype.Component;

@Component
public class IScheduleClientFallback implements IScheduleClient {
    @Override
    public ResponseResult addTask(Task task) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult cancelTask(long taskId) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult poll(int type, int priority) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}