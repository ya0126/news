package com.stu.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.stu.apis.client.IScheduleClient;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.TaskTypeEnum;
import com.stu.model.schedule.dtos.Task;
import com.stu.model.wemedia.pojos.WmNews;
import com.stu.utils.common.ProtostuffUtil;
import com.stu.wemedia.service.WmNewsAutoScanService;
import com.stu.wemedia.service.WmNewsTaskService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 自媒体文章任务业务层实现类
 *
 * @author yaoh
 */
@Service
@Slf4j
public class WmNewsTaskServiceImpl implements WmNewsTaskService {

    private final IScheduleClient scheduleClient;
    private final WmNewsAutoScanService wmNewsAutoScanService;

    public WmNewsTaskServiceImpl(IScheduleClient scheduleClient,
                                 WmNewsAutoScanService wmNewsAutoScanService) {
        this.scheduleClient = scheduleClient;
        this.wmNewsAutoScanService = wmNewsAutoScanService;
    }

    /**
     * 添加任务到延迟队列
     *
     * @param id          文章的id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    @Override
    public void addNewsToTask(Integer id, Date publishTime) {
        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        WmNews wmNews = new WmNews();
        wmNews.setId(id);
        task.setParameters(ProtostuffUtil.serialize(wmNews));
        scheduleClient.addTask(task);
    }

    /**
     * 文章审核
     */
    @Scheduled(fixedRate = 1000)
    @Override
    @SneakyThrows
    public void scanNewsByTask() {
        ResponseResult responseResult = scheduleClient.poll(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        if (responseResult.getCode().equals(200) && responseResult.getData() != null) {
            String json_str = JSON.toJSONString(responseResult.getData());
            Task task = JSON.parseObject(json_str, Task.class);
            byte[] parameters = task.getParameters();
            WmNews wmNews = ProtostuffUtil.deserialize(parameters, WmNews.class);
            wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        }
    }
}
