package com.heima.model.common.enums;

/**
 * 阿里云图片审核服务码
 *
 * @author yaoh
 */
public enum AliImageServiceCode {

    BASELINE_CHECK("baselineCheck", "通用基线检测"),
    BASELINE_CHECK_PRO("baselineCheck_pro", "通用基线检测_专业版"),
    TONALITY_IMPROVE("tonalityImprove", "内容治理检测"),
    PROFILE_PHOTO_CHECK("profilePhotoCheck", "头像图片检测"),
    AIGC_CHECK("aigcCheck", "AIGC图片检测"),
    ADVERTISING_CHECK("advertisingCheck", "营销素材检测"),
    LIVE_STREAM_CHECK("liveStreamCheck", "视频\\直播截图检测"),
    OSS_BASELINE_CHECK("oss_baselineCheck", "OSS基线检测（OSS普惠版专用）");

    private final String serviceCode;
    private final String description;

    AliImageServiceCode(String serviceCode, String description) {
        this.serviceCode = serviceCode;
        this.description = description;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getDescription() {
        return description;
    }
}
