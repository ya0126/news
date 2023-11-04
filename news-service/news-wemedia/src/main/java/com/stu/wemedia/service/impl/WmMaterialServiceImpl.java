package com.stu.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.common.exception.CustomException;
import com.stu.file.service.FileStorageService;
import com.stu.model.common.dtos.PageResponseResult;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.wemedia.dtos.WmMaterialDto;
import com.stu.model.wemedia.pojos.WmMaterial;
import com.stu.model.wemedia.pojos.WmNewsMaterial;
import com.stu.utils.common.WmThreadLocalUtil;
import com.stu.wemedia.mapper.WmMaterialMapper;
import com.stu.wemedia.mapper.WmNewsMaterialMapper;
import com.stu.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 自媒体图文素材信息业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    private final FileStorageService fileStorageService;
    private final WmNewsMaterialMapper wmNewsMaterialMapper;

    public WmMaterialServiceImpl(FileStorageService fileStorageService,
                                 WmNewsMaterialMapper wmNewsMaterialMapper) {
        this.fileStorageService = fileStorageService;
        this.wmNewsMaterialMapper = wmNewsMaterialMapper;
    }

    /**
     * 上传素材
     *
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult upload(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileURL = "";
        try {
            fileURL = fileStorageService.uploadImgFile("material", fileName + postfix, multipartFile.getInputStream());
            log.info("素材上传成功，fileURL:{}", fileURL);
        } catch (IOException e) {
            log.error("素材上传失败", e);
            throw new CustomException(AppHttpCodeEnum.CHANNEL_IN_USE);
        }
        WmMaterial wmMaterial = save(WmThreadLocalUtil.getUser().getId(), fileURL);
        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * 保存素材信息
     *
     * @param userId
     * @param url
     * @return WmMaterial
     */
    private WmMaterial save(Integer userId, String url) {
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(userId);
        wmMaterial.setUrl(url);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);
        return wmMaterial;
    }

    /**
     * 素材列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmMaterialDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();
        if (dto.getIsCollection() != null && dto.getIsCollection() == 1) {
            queryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }
        queryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId()).orderByDesc(WmMaterial::getCreatedTime);
        page = page(page, queryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 删除素材
     *
     * @param materialId
     * @return
     */
    @Override
    public ResponseResult delete(Integer materialId) {
        if (materialId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Integer count = wmNewsMaterialMapper.selectCount(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getMaterialId, materialId));
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "素材被引用，无法删除");
        }
        WmMaterial wmMaterial = getById(materialId);
        String url = wmMaterial.getUrl();
        if (StringUtils.isNoneBlank(url)) {
            fileStorageService.delete(url);
            log.info("素材删除，fileURL:{}", url);
        }
        removeById(materialId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 收藏、取消收藏
     *
     * @param materialId
     * @return
     */
    @Override
    public ResponseResult updateCollection(Integer materialId, Short collection) {
        if (materialId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        WmMaterial wmMaterial = getById(materialId);
        if (wmMaterial == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        wmMaterial.setIsCollection(collection);
        updateById(wmMaterial);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
