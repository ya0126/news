package com.stu.schedule.feign;

import com.stu.apis.client.schedule.IScheduleClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.schedule.dtos.Task;
import com.stu.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    /**
     * 添加任务
     *
     * @param task 任务对象
     * @return
     */
    @Override
    public ResponseResult addTask(Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * 取消任务
     *
     * @param taskId 任务id
     * @return
     */
    @Override
    public ResponseResult cancelTask(long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * 拉取任务
     *
     * @param type
     * @param priority
     * @return
     */
    @Override
    public ResponseResult poll(int type, int priority) {
        return ResponseResult.okResult(taskService.poll(type, priority));
    }
}
