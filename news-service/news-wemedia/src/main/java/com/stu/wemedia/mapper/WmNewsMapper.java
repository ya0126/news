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

    /**
     * 文章列表联合查询(作者信息)
     *
     * @param dto
     * @return List<NewAuthVo>
     */
    List<NewAuthVo> listVo(@Param("dto") NewsAuthDto dto);

    /**
     * 文章联合查询(作者信息)
     *
     * @param newsId
     * @return NewAuthVo
     */
    NewAuthVo getOneVo(Integer newsId);

    /**
     * count查询（配合listVo使用）
     *
     * @param dto
     * @return int
     */
    int findListCount(@Param("dto") NewsAuthDto dto);
}
