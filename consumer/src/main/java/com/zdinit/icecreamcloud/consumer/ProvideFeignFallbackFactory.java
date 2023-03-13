package com.zdinit.icecreamcloud.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProvideFeignFallbackFactory implements FallbackFactory<IProvideFeign> {
    @Override
    public IProvideFeign create(Throwable throwable) {
        log.info("异常原因："+throwable.getMessage());
        return new IProvideFeign() {
            @Override
            public String testApi() {
                log.info("熔断降级");
                return "熔断降级";
            }
        };
    }
}
