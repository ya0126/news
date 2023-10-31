package com.stu.wemedia.service;

import java.util.Date;

/**
 * 自媒体图文信息任务业务层
 *
 * @author yaoh
 */
public interface WmNewsTaskService {

    /**
     * 添加任务到延迟队列中
     *
     * @param id          文章的id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    void addNewsToTask(Integer id, Date publishTime);


    /**
     * 消费延迟队列数据
     */
    void scanNewsByTask();
}
