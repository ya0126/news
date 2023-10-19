package com.heima.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.schedule.pojos.TaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 任务信息mapper
 *
 * @author yaoh
 */
@Mapper
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    public List<TaskInfo> queryFutureTime(@Param("taskType") int type, @Param("priority") int priority, @Param("future") Date future);
}
