package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.vos.NewAuthVo;
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
