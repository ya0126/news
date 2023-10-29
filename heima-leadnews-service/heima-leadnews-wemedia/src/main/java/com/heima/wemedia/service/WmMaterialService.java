package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体图文素材信息业务层
 *
 * @author yaoh
 */
public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 上传素材
     *
     * @param multipartFile
     * @return ResponseResult
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 列表查询
     *
     * @param dto
     * @return ResponseResult
     */
    ResponseResult findList(WmMaterialDto dto);

    /**
     * 删除素材
     *
     * @param materialId
     * @return ResponseResult
     */
    ResponseResult deletePicture(Integer materialId);

    /**
     * 收藏、取消收藏
     *
     * @param materialId
     * @param collection
     * @return ResponseResult
     */
    ResponseResult collection(Integer materialId, Short collection);
}
