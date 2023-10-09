package com.heima.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.ImageModerationRequest;
import com.aliyun.green20220302.models.ImageModerationResponse;
import com.aliyun.green20220302.models.ImageModerationResponseBody;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyData;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyDataResult;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.heima.model.common.dtos.ResponseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
public class NewGreenImageScan {

    private String accessKeyId;
    private String secret;

    public ResponseResult imageScan(String imageUrl, String service) throws Exception {
        Config config = new Config();
        /**
         * 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
         * 常见获取环境变量方式：
         * 方式一：
         *     获取RAM用户AccessKey ID：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         * 方式二：
         *     获取RAM用户AccessKey ID：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         */
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        // 接入区域和地址请根据实际情况修改。
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        // 连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        // 读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        // 设置http代理。
        // config.setHttpProxy("http://10.10.xx.xx:xxxx");
        // 设置https代理。
        //config.setHttpsProxy("https://10.10.xx.xx:xxxx");
        // 注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能。
        Client client = new Client(config);

        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        // 检测参数构造。
        Map<String, String> serviceParameters = new HashMap<>();
        //公网可访问的URL。
        serviceParameters.put("imageUrl", imageUrl);
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
                if (response.getStatusCode() == 200) {
                    ImageModerationResponseBody body = response.getBody();
                    log.info("阿里云图片检测服务调用，requestId:{}", body.getRequestId());
                    if (body.getCode() == 200) {
                        ImageModerationResponseBodyData data = body.getData();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataId", data.getDataId());
                        JSONArray objects = new JSONArray();
                        List<ImageModerationResponseBodyDataResult> results = data.getResult();
                        for (ImageModerationResponseBodyDataResult result : results) {
                            JSONObject resultData = new JSONObject();
                            resultData.put("label", result.getLabel());
                            resultData.put("confidence", result.getConfidence());
                            objects.add(resultData);
                        }
                        jsonObject.put("data", objects);
                        return ResponseResult.okResult(jsonObject);
                    } else {
                        return ResponseResult.errorResult(body.getCode(), "image moderation not success");
                    }
                } else {
                    return ResponseResult.errorResult(response.getStatusCode(), "response not success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
