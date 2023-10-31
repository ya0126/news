package com.stu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.admin.pojos.AdUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author yaoh
 */
@Mapper
public interface AdUserMapper extends BaseMapper<AdUser> {
}
