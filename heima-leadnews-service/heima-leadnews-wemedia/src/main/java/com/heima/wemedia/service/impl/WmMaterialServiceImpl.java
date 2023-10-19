package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 自媒体图文素材信息-业务层service实现类
 *
 * @author yaoh
 */
@Service
@Slf4j
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        if (multipartFile == null || multipartFile.getSize() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        String fileName = UUID.randomUUID().toString().replace("-", "");

        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;

        try {
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}", fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");
        }

        WmMaterial wmMaterial = new WmMaterial();
        // TODO 可能为空
        //wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);

        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * 列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmMaterialDto dto) {

        dto.checkParam();

        // 分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();

        // 是否收藏
        if (dto.getIsCollection() != null && dto.getIsCollection() == 1) {
            queryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }

        // 根据用户查找
        // TODO 有bug待解决，threadLocal获取不到用户id，原因是拦截器没有从request请求头中获取到userId。可能是前端问题
        //queryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId());

        // 根据时间排序
        queryWrapper.orderByDesc(WmMaterial::getCreatedTime);

        page = page(page, queryWrapper);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 删除文件
     *
     * @param materialId
     * @return
     */
    @Override
    public ResponseResult deletePicture(Integer materialId) {

        if (materialId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmMaterial wmMaterial = getById(materialId);

        String url = wmMaterial.getUrl();
        if (StringUtils.isNoneBlank(url)) {
            // 删除文件系统图片
            fileStorageService.delete(url);
        }

        // 删除数据库保存的图片信息
        removeById(materialId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 图片收藏
     *
     * @param materialId
     * @return
     */
    @Override
    public ResponseResult collect(Integer materialId) {

        if (materialId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmMaterial wmMaterial = getById(materialId);

        if (wmMaterial != null && wmMaterial.getIsCollection() != null) {
            wmMaterial.setIsCollection((short) 1);
            updateById(wmMaterial);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 取消收藏图片
     * @param materialId
     * @return
     */
    @Override
    public ResponseResult cancelCollect(Integer materialId) {
        if (materialId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmMaterial wmMaterial = getById(materialId);

        if (wmMaterial != null && wmMaterial.getIsCollection() != null) {
            wmMaterial.setIsCollection((short) 0);
            updateById(wmMaterial);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
