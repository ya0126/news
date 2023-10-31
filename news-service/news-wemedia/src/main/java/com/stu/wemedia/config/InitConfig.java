package com.stu.wemedia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 初始配置
 *
 * @author yaoh
 */
@Configuration
@ComponentScan({"com.stu.apis.article.fallback", "com.stu.apis.schedule.fallback"})
public class InitConfig {
}
