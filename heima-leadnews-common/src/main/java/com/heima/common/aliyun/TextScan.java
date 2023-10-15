package com.heima.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.TextModerationRequest;
import com.aliyun.green20220302.models.TextModerationResponse;
import com.aliyun.green20220302.models.TextModerationResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云内容安全服务增强版
 * 文本检测
 *
 * @author yaoh
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
@Slf4j
public class TextScan {

    private String accessKeyId;
    private String secret;

    public Map scan(String content, String serviceCode) throws Exception {
        Config config = new Config();

        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        //接入区域和地址请根据实际情况修改
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        //连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        //读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);

        // 注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能
        Client client = new Client(config);

        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        //检测参数构造
        JSONObject serviceParameters = new JSONObject();
        serviceParameters.put("content", content);

        if (serviceParameters.get("content") == null || serviceParameters.getString("content").trim().length() == 0) {
            return null;
        }

        TextModerationRequest textModerationRequest = new TextModerationRequest();
        /*
        文本检测服务 service code
        */
        textModerationRequest.setService(serviceCode);
        textModerationRequest.setServiceParameters(serviceParameters.toJSONString());

        Map<String, String> resultMap = new HashMap<>();
        try {
            // 调用方法获取检测结果。
            TextModerationResponse response = client.textModerationWithOptions(textModerationRequest, runtime);

            // 自动路由。
            if (response != null) {
                // 服务端错误，区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    config.setRegionId("cn-beijing");
                    config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
                    client = new Client(config);
                    response = client.textModerationWithOptions(textModerationRequest, runtime);
                }

            }
            // 打印检测结果。
            if (response != null && response.getStatusCode() == 200) {
                TextModerationResponseBody result = response.getBody();
                log.info("requestId: {},阿里云文本检测服务调用", JSON.toJSONString(result.getRequestId()));

                Integer code = result.getCode();
                if (code != null && code == 200) {
                    TextModerationResponseBody.TextModerationResponseBodyData data = result.getData();
                    String labels = data.getLabels();

                    if (labels.isEmpty()) {
                        log.info("requestId:{},审核通过", result.getRequestId());
                        resultMap.put("suggestion", "pass");
                        return resultMap;
                    }

                    JSONObject jsonObject = JSONObject.parseObject(data.getReason());
                    String riskTips = jsonObject.getString("riskTips");
                    String riskWords = jsonObject.getString("riskWords");

                    resultMap.put("suggestion", "block");
                    resultMap.put("labels", labels);
                    resultMap.put("riskTips", riskTips);
                    resultMap.put("riskWords", riskWords);
                    log.info("requestId:{},审核未通过", result.getRequestId());
                    return resultMap;
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
