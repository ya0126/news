package com.heima.schedule.service;

import com.heima.model.schedule.dtos.Task;

/**
 * 任务服务类
 *
 * @author yaoh
 */
public interface TaskService {

    /**
     * 添加任务
     *
     * @param task
     * @return long
     */
    long addTask(Task task);

    /**
     * 取消任务
     *
     * @param taskId
     * @return boolean
     */
    boolean cancelTask(long taskId);

    /**
     * 按照类型和优先级来拉取任务
     *
     * @param type
     * @param priority
     * @return
     */
    Task poll(int type, int priority);
}
