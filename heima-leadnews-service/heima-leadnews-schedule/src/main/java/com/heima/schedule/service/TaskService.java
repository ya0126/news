package com.heima.schedule.service;

import com.heima.model.schedule.dtos.Task;

/**
 * TODO
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
    public long addTask(Task task);

    /**
     * 取消任务
     *
     * @param taskId
     * @return boolean
     */
    public boolean cancelTask(long taskId);
}
