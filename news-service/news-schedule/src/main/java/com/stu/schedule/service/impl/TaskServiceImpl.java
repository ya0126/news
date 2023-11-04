package com.stu.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stu.common.constants.ScheduleConstants;
import com.stu.common.redis.CacheService;
import com.stu.model.schedule.dtos.Task;
import com.stu.model.schedule.pojos.TaskInfo;
import com.stu.model.schedule.pojos.TaskinfoLogs;
import com.stu.schedule.mapper.TaskInfoLogsMapper;
import com.stu.schedule.mapper.TaskInfoMapper;
import com.stu.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 任务服务实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskInfoMapper taskInfoMapper;
    private final TaskInfoLogsMapper taskInfoLogsMapper;
    private final CacheService cacheService;

    public TaskServiceImpl(TaskInfoMapper taskInfoMapper,
                           TaskInfoLogsMapper taskInfoLogsMapper,
                           CacheService cacheService) {
        this.taskInfoMapper = taskInfoMapper;
        this.taskInfoLogsMapper = taskInfoLogsMapper;
        this.cacheService = cacheService;
    }

    /**
     * 添加任务
     *
     * @param task
     * @return
     */
    @Override
    public long addTask(Task task) {
        addTaskToDB(task);
        addTaskToCache(task);
        return task.getTaskId();
    }

    /**
     * 添加任务到数据库
     *
     * @param task
     * @return boolean
     */
    private void addTaskToDB(Task task) {
        try {
            // 保存任务信息
            TaskInfo taskInfo = new TaskInfo();
            BeanUtils.copyProperties(task, taskInfo);
            taskInfo.setExecuteTime(new Date(task.getExecuteTime()));
            taskInfoMapper.insert(taskInfo);

            // 设置taskId
            task.setTaskId(taskInfo.getTaskId());

            // 保存任务日志信息
            TaskinfoLogs taskinfoLogs = new TaskinfoLogs();
            BeanUtils.copyProperties(taskInfo, taskinfoLogs);
            taskinfoLogs.setVersion(1);
            taskinfoLogs.setStatus(ScheduleConstants.SCHEDULED);
            taskInfoLogsMapper.insert(taskinfoLogs);
        } catch (Exception e) {
            log.error("添加任务至数据库失败", e);
        }
    }

    /**
     * 添加任务至redis
     *
     * @param task
     */
    private void addTaskToCache(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();
        // 获取5分钟之后的 时间  毫秒值
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        long nextScheduleTime = calendar.getTimeInMillis();

        // 如果任务的执行时间小于等于当前时间，存入list(任务队列)
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lLeftPush(ScheduleConstants.TOPIC + key, JSON.toJSONString(task));
        } else if (task.getExecuteTime() <= nextScheduleTime) {
            // 如果任务的执行时间大于当前时间 && 小于等于预设时间（未来5分钟） 存入ZSet中(存储未来任务，以执行时间排序)
            cacheService.zAdd(ScheduleConstants.FUTURE + key, JSON.toJSONString(task), task.getExecuteTime());
        }
    }

    /**
     * 取消任务
     *
     * @param taskId
     * @return
     */
    @Override
    public boolean cancelTask(long taskId) {
        boolean flag = false;
        // 删除任务的数据库信息，更新任务的日志的数据库信息
        Task task = updateDB(taskId, ScheduleConstants.CANCELLED);
        if (task != null) {
            removeTaskFromCache(task);
            flag = true;
        }
        return flag;
    }

    /**
     * 删除任务的数据库信息，更新任务的日志的数据库信息
     *
     * @param taskId
     * @param status
     * @return boolean
     */
    private Task updateDB(long taskId, int status) {
        Task task = null;
        try {
            // 1.删除任务信息
            taskInfoMapper.deleteById(taskId);
            // 2.修改任务日志
            TaskinfoLogs taskinfoLogs = taskInfoLogsMapper.selectById(taskId);
            taskinfoLogs.setStatus(status);
            taskInfoLogsMapper.deleteById(taskId);

            task = new Task();
            BeanUtils.copyProperties(taskinfoLogs, task);
            task.setExecuteTime(taskinfoLogs.getExecuteTime().getTime());
        } catch (Exception e) {
            log.error("删除任务的数据库信息，更新任务的日志的数据库信息失败", e);
        }
        return task;
    }

    /**
     * 删除redis中的任务数据
     *
     * @param task
     */
    private void removeTaskFromCache(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lRemove(ScheduleConstants.TOPIC + key, 0, JSON.toJSONString(task));
        } else {
            cacheService.zRemove(ScheduleConstants.FUTURE + key, JSON.toJSONString(task));
        }
    }

    /**
     * 按类型和权重拉取任务
     *
     * @param type
     * @param priority
     * @return
     */
    @Override
    public Task poll(int type, int priority) {
        Task task = null;
        try {
            String key = type + "_" + priority;
            // 从redis中获取任务信息
            String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC + key);
            if (StringUtils.isNoneBlank(task_json)) {
                task = JSON.parseObject(task_json, Task.class);
                // 修改数据库中的任务信息
                updateDB(task.getTaskId(), ScheduleConstants.EXECUTED);
            }
        } catch (Exception e) {
            log.error("拉取任务失败", e);
        }
        return task;
    }

    /**
     * 将即将执行的任务从ZSet刷新到list中，每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        log.info("{}执行了定时任务", format.format(new Date()));
        String token = cacheService.tryLock("FUTURE_TASK_SYNC", 1000 * 30);
        if (StringUtils.isNoneBlank(token)) {
            // 获取所有未来数据集合的key值
            Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_*
            for (String futureKey : futureKeys) { // future_250_250
                String topicKey = ScheduleConstants.TOPIC + futureKey.split(ScheduleConstants.FUTURE)[1];
                // 获取该组key下当前需要消费的任务数据
                Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());
                if (!tasks.isEmpty()) {
                    // 将这些任务数据添加到消费者队列中
                    cacheService.refreshWithPipeline(futureKey, topicKey, tasks);
                    log.info("成功的将{},下的当前需要执行的任务数据刷新到{}下", futureKey, topicKey);
                }
            }
        }
    }

    /**
     * 数据库数据同步到缓存,每五分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    @PostConstruct
    public void reloadData() {
        clearCache();
        log.info("数据库数据同步开始：将未来5分钟任务同步到缓存");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        List<TaskInfo> taskInfos = taskInfoMapper.selectList(Wrappers
                .<TaskInfo>lambdaQuery()
                .lt(TaskInfo::getExecuteTime, calendar.getTime()));
        if (taskInfos != null && !taskInfos.isEmpty()) {
            for (TaskInfo taskInfo : taskInfos) {
                Task task = new Task();
                BeanUtils.copyProperties(taskInfo, task);
                task.setExecuteTime(taskInfo.getExecuteTime().getTime());
                addTaskToCache(task);
            }
        }
        log.info("数据库数据同步完成，任务数量{}", taskInfos.size());
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        // 删除缓存中未来数据集合和当前消费者队列的所有key
        Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
        Set<String> topicKeys = cacheService.scan(ScheduleConstants.TOPIC + "*");
        cacheService.delete(futureKeys);
        cacheService.delete(topicKeys);
    }
}
