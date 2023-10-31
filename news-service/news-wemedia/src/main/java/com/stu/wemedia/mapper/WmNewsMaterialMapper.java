package com.stu.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自媒体文章和素材关联mapper
 *
 * @author yaoh
 */
@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    void saveRelations(@Param("materialIds") List<Integer> materialIds, @Param("newsId") Integer newsId, @Param("type") Short type);
}
