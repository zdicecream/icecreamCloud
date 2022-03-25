package com.zdinit.icecreamcloud.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteHystrix implements IProvideFeign{
    @Override
    public void testApi() {
        log.info("请求超时了");
    }
}
