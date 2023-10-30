package com.heima.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
/**
 * 之所以排除DataSourceAutoConfiguration.class，
 * 是因为依赖了utils模块，utils依赖了model模块，model有mybatis相关依赖，
 * 但是app网关并没有数据库相关配置，如果不排除会启动报错
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AppGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppGatewayApplication.class, args);
    }
}
