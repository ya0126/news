package com.stu.schedule.service;


import com.stu.model.schedule.dtos.Task;

/**
 * 任务业务层
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
