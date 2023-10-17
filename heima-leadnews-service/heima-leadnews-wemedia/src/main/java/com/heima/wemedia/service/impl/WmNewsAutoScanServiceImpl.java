package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.apis.article.IArticleClient;
import com.heima.common.aliyun.ImageScan;
import com.heima.common.aliyun.TextScan;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AliImageServiceCode;
import com.heima.model.common.enums.AliTextServiceCode;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.SensitiveWordUtil;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author yaoh
 */
@Slf4j
@Service
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * 自媒体文章审核
     *
     * @param id 自媒体文章id
     */
    @Override
    @Async  // 标明当前方法是一个异步方法
    public void autoScanWmNews(Integer id) {

        // 查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);

        if (wmNews == null) {
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }

        //
        if (wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())) {
            // 1.从内容中提取纯文本内容和图片
            Map<String, Object> textAndImages = handleTextAndImages(wmNews);


            // 2.自管理的敏感词过滤
            boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"), wmNews);
            if (!isSensitive) return;

            // 3.审核文本内容
            boolean isTextScan = handleTextScan((String) textAndImages.get("content"), wmNews);
            if (!isTextScan) return;

            // 4.审核图片
            boolean isImageScan = handleImageScan((List<String>) textAndImages.get("images"), wmNews);
            if (!isImageScan) return;

            // 5.审核成功，保存app端的相关的文章数据
            ResponseResult responseResult = saveAppArticle(wmNews);

            // 回填article_id
            wmNews.setArticleId((Long) responseResult.getData());
            updateWmNews(wmNews, (short) 9, "审核成功");
        }


    }

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;

    /**
     * 自管理的敏感词审核
     *
     * @param content
     * @param wmNews
     * @return boolean
     */
    private boolean handleSensitiveScan(String content, WmNews wmNews) {

        boolean flag = true;

        // 查询所有敏感词
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives));
        List<String> sensitives = wmSensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());

        //初始化敏感词库
        SensitiveWordUtil.initMap(sensitives);

        //查看文章中是否包含敏感词
        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if (map.size() > 0) {
            updateWmNews(wmNews, (short) 2, "当前文章中存在违规内容" + map);
            flag = false;
        }
        return flag;
    }

    @Autowired
    private IArticleClient articleClient;

    @Autowired
    private WmChannelMapper wmChannelMapper;

    @Autowired
    WmUserMapper wmUserMapper;

    /**
     * 保存app端相关的文章数据
     *
     * @param wmNews
     * @return ResponseResult
     */
    private ResponseResult saveAppArticle(WmNews wmNews) {

        ArticleDto articleDto = new ArticleDto();

        BeanUtils.copyProperties(wmNews, articleDto);

        //文章的布局
        articleDto.setLayout(wmNews.getType());
        //频道
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if (wmChannel != null) {
            articleDto.setChannelName(wmChannel.getName());
        }

        //作者
        //articleDto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if (wmUser != null) {
            articleDto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if (wmNews.getArticleId() != null) {
            articleDto.setId(wmNews.getArticleId());
        }
        articleDto.setCreatedTime(new Date());

        ResponseResult responseResult = articleClient.saveArticle(articleDto);
        return responseResult;
    }

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ImageScan imageScan;

    /**
     * 审核图片内容 阿里云
     *
     * @param images
     * @param wmNews
     * @return boolean
     */
    private boolean handleImageScan(List<String> images, WmNews wmNews) {

        boolean flag = true;

        if (images == null || images.size() == 0) {
            return flag;
        }

        // 图片去重
        List<String> imagesUrlList = images.stream().distinct().collect(Collectors.toList());

        for (String imageUrl : imagesUrlList) {

            InputStream inputStream = fileStorageService.downLoadFile(imageUrl, InputStream.class);
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"), imageUrl.length() - 1);

            try {
                Map result = imageScan.scan(fileName, inputStream, AliImageServiceCode.BASELINE_CHECK.getServiceCode());

                if (result.get("suggestion").equals("block")) {
                    flag = false;
                    updateWmNews(wmNews, (short) 3, "当前文章中存在违规内容");
                }

            } catch (Exception e) {

                throw new RuntimeException(e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Autowired
    private TextScan textScan;

    /**
     * 审核文本内容 阿里云
     *
     * @param content
     * @param wmNews
     * @return boolean
     */
    private boolean handleTextScan(String content, WmNews wmNews) {

        boolean flag = true;

        if ((wmNews.getTitle() + "-" + content).length() == 0) {
            return flag;
        }

        try {
            Map result = textScan.scan(content + "-" + wmNews.getTitle(), AliTextServiceCode.COMMENT_DETECTION.getServiceCode());

            /**
             * 审核失败
             *
             * 因为用的是阿里云内容安全增强服务，返回结果没有suggestion，无法根据suggestion=review，来进行人工审核，
             * 所以这里给每个没有审核通过的wmNews的status改为3，提供一次人工审核机会
             */
            if (result.get("suggestion").equals("block")) {
                flag = false;
                updateWmNews(wmNews, (short) 3, "以下词汇涉嫌违规：" + result.get("riskWords") + "。需要人工审核。");
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 修改文章内容
     *
     * @param wmNews
     * @param status 状态
     * @param reason 拒绝理由
     */
    private void updateWmNews(WmNews wmNews, short status, String reason) {
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

    /**
     * 1.从自媒体文章的内容中提取文本和图片
     * 2.提取文章的封面图片
     *
     * @param wmNews
     * @return Map<String, Object>
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {

        // 存储文章内容
        StringBuilder stringBuilder = new StringBuilder();

        String content = wmNews.getContent();

        List<String> images = new ArrayList<>();
        if (StringUtils.isNoneBlank(content)) {
            List<Map> maps = JSONArray.parseArray(content, Map.class);
            for (Map map : maps) {
                // 从自媒体文章内容中提取文字
                if (map.get("type").equals("text")) {
                    stringBuilder.append(map.get("value"));
                }
                // 从自媒体文章内容中提取图片
                if (map.get("type").equals("image")) {
                    images.add((String) map.get("value"));
                }
            }
        }

        // 提取文章封面图片
        if (StringUtils.isNotBlank(wmNews.getImages())) {
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content", stringBuilder.toString());
        resultMap.put("images", images);
        return resultMap;
    }
}
