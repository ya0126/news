package com.stu.apis.config;

import com.stu.apis.factory.IArticleClientFallbackFactory;
import com.stu.apis.factory.IScheduleClientFallbackFactory;
import com.stu.apis.factory.IWemediaClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level level() {
        return Logger.Level.BASIC;
    }

    @Bean
    public IArticleClientFallbackFactory iArticleClientFallbackFactory() {
        return new IArticleClientFallbackFactory();
    }

    @Bean
    public IScheduleClientFallbackFactory iScheduleClientFallbackFactory() {
        return new IScheduleClientFallbackFactory();
    }

    @Bean
    public IWemediaClientFallbackFactory iWemediaClientFallbackFactory() {
        return new IWemediaClientFallbackFactory();
    }
}
