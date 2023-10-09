package com.heima.model.common.enums;

/**
 * 阿里云内容安全服务枚举类
 *
 * @author yaoh
 */
public enum AliTextServiceCode {
    AD_COMPLIANCE_DETECTION("ad_compliance_detection", "广告法合规检测"),
    AI_ART_DETECTION("ai_art_detection", "AIGC类文字检测"),
    CHAT_DETECTION("chat_detection", "私聊互动内容检测"),
    COMMENT_DETECTION("comment_detection", "公聊评论内容检测"),
    COMMENT_MULTILINGUAL_PRO("comment_multilingual_pro", "国际业务多语言检测"),
    NICKNAME_DETECTION("nickname_detection", "用户昵称检测"),
    PGC_DETECTION("pgc_detection", "PGC教学物料检测");

    private final String serviceCode;
    private final String description;

    AliTextServiceCode(String serviceCode, String description) {
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
