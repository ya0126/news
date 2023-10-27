package com.heima.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.schedule.pojos.TaskinfoLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务日志mapper
 *
 * @author yaoh
 */
@Mapper
public interface TaskInfoLogsMapper extends BaseMapper<TaskinfoLogs> {
}
