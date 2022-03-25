package com.zdinit.icecreamcloud.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class NacosConfigController {

    @Value("${spring.application.name}")
    private String springApplicationName;
}
