package com.stu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author yaoh
 */
@Mapper
public interface ApUserMapper extends BaseMapper<ApUser> {
}
