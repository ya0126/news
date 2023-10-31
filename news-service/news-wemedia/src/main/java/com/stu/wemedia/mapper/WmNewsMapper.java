package com.stu.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.wemedia.dtos.NewsAuthDto;
import com.stu.model.wemedia.pojos.WmNews;
import com.stu.model.wemedia.vos.NewAuthVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自媒体文章mapper
 *
 * @author yaoh
 */
@Mapper
public interface WmNewsMapper extends BaseMapper<WmNews> {

    List<NewAuthVo> newsAuthList(@Param("dto") NewsAuthDto dto);

    NewAuthVo getOneVo(Integer newsId);

    int findListCount(@Param("dto") NewsAuthDto dto);
}
