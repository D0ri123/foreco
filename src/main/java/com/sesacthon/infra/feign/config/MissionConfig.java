package com.sesacthon.infra.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.sesacthon.infra.feign.client.mission")
public class MissionConfig {

}
