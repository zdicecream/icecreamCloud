package com.zdinit.icecreamcloud.provide.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class NacosConfigController {

    @Value("${test:1212}")
    private String springApplicationName;

    @RequestMapping("/get")
    public String get() {
        return springApplicationName;
    }
}
