package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.WemediaConstants;
import com.heima.common.constants.WmNewsMessageConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.vos.NewAuthVo;
import com.heima.utils.common.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import com.heima.wemedia.service.WmNewsTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自媒体图文内容信息业务层service实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Override
    public ResponseResult newsAuthPageQuery(NewsAuthDto dto) {
        List<NewAuthVo> newAuthVos = wmNewsMapper.newsAuthList(dto);
        return ResponseResult.okResult(newAuthVos);
    }

    /**
     * 查询所有自媒体图文
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {

        // 检查dto参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 检车登录用户
        //WmUser user = WmThreadLocalUtil.getUser();
        //if (user == null) {
        //    return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        //}

        // 检查分页参数
        dto.checkParam();

        // 分页查询条件
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> queryWrapper = new LambdaQueryWrapper<>();

        // 状态精确查找
        if (dto.getStatus() != null) {
            queryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        // 频道精确查找
        if (dto.getChannelId() != null) {
            queryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        // 时间范围查找
        if (dto.getBeginPubDate() != null && dto.getEndPubDate() != null) {
            queryWrapper.between(WmNews::getPublishTime, dto.getBeginPubDate(), dto.getEndPubDate());
        }

        // 关键字模糊查找
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            queryWrapper.like(WmNews::getTitle, dto.getKeyword());
        }

        //查询当前登录用户的文章
        //queryWrapper.eq(WmNews::getUserId, user.getId());

        //发布时间倒序查询
        queryWrapper.orderByDesc(WmNews::getCreatedTime);

        page = page(page, queryWrapper);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Autowired
    private WmNewsTaskService wmNewsTaskService;

    /**
     * 发布文章或保存草稿
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult submitNews(WmNewsDto dto) {
        // 1.参数检查
        if (dto == null || dto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto, wmNews);

        // 2.封面图片处理
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            String imageStr = StringUtils.join(dto.getImages(), ",");
            wmNews.setImages(imageStr);
        }

        // 3.如果当前封面类型为自动 -1
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            wmNews.setType(null);
        }

        saveOrUpdateWmNews(wmNews);

        // 4.判断是否为草稿  如果为草稿结束当前方法
        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        // 5.不是草稿，保存文章内容图片与素材的关系
        // 获取到文章内容中的图片信息
        List<String> materials = ectractUrlInfo(dto.getContent());
        saveRelativeInfoForContent(materials, wmNews.getId());

        // 审核代码
        //wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        wmNewsTaskService.addNewsToTask(wmNews.getId(), wmNews.getPublishTime());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 保存或修改文章
     *
     * @param wmNews
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        // 补全属性
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);//默认上架

        if (wmNews.getId() == null) {
            // 保存
            save(wmNews);
        } else {
            // 修改
            // 删除文章图片与素材的关系
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, wmNews.getId()));
            updateById(wmNews);
        }
    }

    /**
     * 提取文章内容中的图片信息
     *
     * @param content
     * @return List<String>
     */
    private List<String> ectractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();

        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps) {
            if (map.get("type").equals("image")) {
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }

        return materials;
    }

    /**
     * 处理文章内容图片与素材的关系
     *
     * @param materials
     * @param newsId
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        saveRelativeInfo(materials, newsId, WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     * 保存文章图片与素材的关系到数据库中
     *
     * @param materials
     * @param newsId
     * @param type
     */
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if (materials != null && !materials.isEmpty()) {
            //通过图片的url查询素材的id
            List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

            //判断素材是否有效
            if (dbMaterials == null || dbMaterials.isEmpty()) {
                //手动抛出异常   第一个功能：能够提示调用者素材失效了，第二个功能，进行数据的回滚
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }
            if (materials.size() != dbMaterials.size()) {
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }
            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());
            //批量保存
            wmNewsMaterialMapper.saveRelations(idList, newsId, type);
        }
    }

    /**
     * 根据id删除文章
     *
     * @param newsId
     * @return
     */
    @Override
    public ResponseResult deleteNewsById(Integer newsId) {
        // 1.参数检查
        if (newsId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 2.删除文章
        removeById(newsId);
        // 3.删除文章关联的素材信息
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, newsId));
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        if (dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }
        if (!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前文章不是发布状态，不能上下架");
        }
        if (dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2) {
            update(Wrappers.<WmNews>lambdaUpdate()
                    .set(WmNews::getEnable, dto.getEnable())
                    .eq(WmNews::getId, dto.getId()));
        }
        //发送消息，通知article端修改文章配置
        if (wmNews.getArticleId() != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("articleId", wmNews.getArticleId());
            map.put("enable", dto.getEnable());
            kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC, JSON.toJSONString(map));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 文章联合查询
     *
     * @param newsId
     * @return
     */
    @Override
    public ResponseResult getOneVo(Integer newsId) {
        if (newsId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        NewAuthVo newAuthVo = wmNewsMapper.getOneVo(newsId);
        return ResponseResult.okResult(newAuthVo);
    }

    /**
     * 审核未通过
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authFail(NewsAuthDto dto) {
        // 1.校验参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.修改
        LambdaUpdateWrapper<WmNews> wrapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNotBlank(dto.getMessage())) {
            wrapper.set(WmNews::getReason, dto.getMessage());
        }
        wrapper.set(WmNews::getStatus, (short) 2);
        wrapper.eq(WmNews::getId, dto.getId());
        update(wrapper);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 审核通过
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authPass(NewsAuthDto dto) {
        // 1.校验参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        // 2.修改
        LambdaUpdateWrapper<WmNews> wrapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNotBlank(dto.getMessage())) {
            wrapper.set(WmNews::getReason, dto.getMessage());
        }
        wrapper.set(WmNews::getStatus, (short) 8);
        wrapper.eq(WmNews::getId, dto.getId());
        update(wrapper);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
