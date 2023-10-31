package com.stu.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自媒体用户mapper
 */
@Mapper
public interface WmUserMapper extends BaseMapper<WmUser> {
}