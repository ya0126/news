package com.heima.schedule.feign;

import com.heima.apis.article.IScheduleClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.schedule.dtos.Task;
import com.heima.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author yaoh
 */
@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    @Override
    public ResponseResult addTask(Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    @Override
    public ResponseResult cancelTask(long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    @Override
    public ResponseResult poll(int type, int priority) {
        return ResponseResult.okResult(taskService.poll(type, priority));
    }
}
