package com.heima.behavior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户行为引导类
 *
 * @author yaoh
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BehaviorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BehaviorApplication.class, args);
    }
}