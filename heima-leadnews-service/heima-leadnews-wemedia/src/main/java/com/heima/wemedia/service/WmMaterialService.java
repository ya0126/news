package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @author yaoh
 */
public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 文件上传
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
}
