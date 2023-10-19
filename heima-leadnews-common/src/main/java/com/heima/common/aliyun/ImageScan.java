package com.heima.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.*;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyData;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyDataResult;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * 阿里云内容安全服务增强版
 * 图片检测
 *
 * @author yaoh
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
@Slf4j
public class ImageScan {

    private String accessKeyId;
    private String secret;
    private static String bucketName;
    public static DescribeUploadTokenResponseBody.DescribeUploadTokenResponseBodyData uploadToken = null;

    public Map scan(String fileName, InputStream inputStream, String service) throws Exception {
        //服务是否部署在vpc上。
        boolean isVPC = false;

        Config config = new Config();

        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);

        // 接入区域和地址请根据实际情况修改。
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        // 连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        // 读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        // 注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能。
        Client client = new Client(config);
        //获取文件上传token。
        if (uploadToken == null || uploadToken.expiration <= System.currentTimeMillis() / 1000) {
            DescribeUploadTokenResponse tokenResponse = client.describeUploadToken();
            uploadToken = tokenResponse.getBody().getData();
            bucketName = uploadToken.getBucketName();
        }
        //上传文件。
        String objectName = uploadFile(fileName, inputStream, uploadToken, isVPC);
        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        // 检测参数构造。
        Map<String, String> serviceParameters = new HashMap<>();
        serviceParameters.put("ossBucketName", bucketName);
        serviceParameters.put("ossObjectName", objectName);
        serviceParameters.put("dataId", UUID.randomUUID().toString());

        ImageModerationRequest request = new ImageModerationRequest();
        // 图片检测service: baselineCheck通用基线检测。
        request.setService(service);
        request.setServiceParameters(JSON.toJSONString(serviceParameters));

        try {
            ImageModerationResponse response = client.imageModerationWithOptions(request, runtime);
            // 自动路由。
            if (response != null) {
                // 服务端错误，区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    config.setRegionId("cn-beijing");
                    config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
                    client = new Client(config);
                    response = client.imageModerationWithOptions(request, runtime);
                }
            }

            // 打印检测结果。
            if (response != null) {
                ImageModerationResponseBody body = response.getBody();
                log.info("requestId: {},阿里云文本检测服务调用", JSON.toJSONString(body.getRequestId()));

                if (response.getStatusCode() == 200 && body.getCode() == 200) {
                    ImageModerationResponseBodyData data = body.getData();
                    List<ImageModerationResponseBodyDataResult> results = data.getResult();

                    if (results.size() == 1 && results.get(0).getLabel().equals("nonLabel")) {
                        log.info("requestId:{}，审核通过",JSON.toJSONString(body.getRequestId()));
                        return Collections.singletonMap("suggestion", "pass");
                    }

                    Map<String, Object> resultMap = new HashMap<>();
                    log.info("requestId:{}，审核未通过",JSON.toJSONString(body.getRequestId()));
                    resultMap.put("suggestion", "block");
                    for (ImageModerationResponseBodyDataResult result : results) {
                        resultMap.put(result.getLabel(), result.getConfidence());
                    }
                    return resultMap;
                }
            }

            return null;
        } catch (Exception e) {
            log.error("阿里云图片审核失败",e);
        }
        return null;
    }

    public static String uploadFile(String fileName, InputStream inputStream, DescribeUploadTokenResponseBody.DescribeUploadTokenResponseBodyData tokenData, boolean isVPC) throws Exception {
        OSS ossClient = null;
        if (isVPC) {
            ossClient = new OSSClientBuilder().build(tokenData.ossInternalEndPoint, tokenData.getAccessKeyId(), tokenData.getAccessKeySecret(), tokenData.getSecurityToken());
        } else {
            ossClient = new OSSClientBuilder().build(tokenData.ossInternetEndPoint, tokenData.getAccessKeyId(), tokenData.getAccessKeySecret(), tokenData.getSecurityToken());
        }

        String objectName = tokenData.getFileNamePrefix() + UUID.randomUUID() + "." + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,inputStream);
        ossClient.putObject(putObjectRequest);
        return objectName;
    }
}
