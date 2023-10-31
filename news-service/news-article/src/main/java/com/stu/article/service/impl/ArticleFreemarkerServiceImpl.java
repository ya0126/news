package com.stu.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stu.article.service.ApArticleService;
import com.stu.article.service.ArticleFreemarkerService;
import com.stu.common.constants.ArticleConstants;
import com.stu.file.service.FileStorageService;
import com.stu.model.article.pojos.ApArticle;
import com.stu.model.search.vos.SearchArticleVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章生成静态文件并上传至minio服务实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {

    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ApArticleService apArticleService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 生成静态文件上传到minio
     *
     * @param apArticle
     * @param content
     */
    @Async
    @Override
    public void buildArticleToMinIO(ApArticle apArticle, String content) {
        // 1.获取文章内容
        if (StringUtils.isNoneBlank(content)) {
            // 2.文章内容通过freemarker生成html文件
            Template template = null;
            StringWriter out = new StringWriter();
            try {
                template = configuration.getTemplate("article.ftl");
                // 2.1 数据模型
                Map<String, Object> contentDataModel = new HashMap<>();
                contentDataModel.put("content", JSONArray.parseArray(content));
                // 2.2 合成
                template.process(contentDataModel, out);
            } catch (Exception e) {
                log.info("生成freemarker文件失败", e);
            }
            // 3.把html文件上传到minio中
            InputStream in = new ByteArrayInputStream(out.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("html", apArticle.getId() + ".html", in);

            // 4.修改ap_article表，保存static_url字段
            apArticleService
                    .update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId, apArticle.getId())
                            .set(ApArticle::getStaticUrl, path));
            //发送消息，创建索引
            createArticleESIndex(apArticle, content, path);
        }
    }

    /**
     * 送消息，创建索引
     *
     * @param apArticle
     * @param content
     * @param path
     */
    private void createArticleESIndex(ApArticle apArticle, String content, String path) {
        SearchArticleVo vo = new SearchArticleVo();
        BeanUtils.copyProperties(apArticle, vo);
        vo.setContent(content);
        vo.setStaticUrl(path);

        kafkaTemplate.send(ArticleConstants.ARTICLE_ES_SYNC_TOPIC, JSON.toJSONString(vo));
        log.info("发送消息至search服务,添加文章索引，文章ID为：{}", apArticle.getId());
    }
}
