package com.zdinit.icecreamcloud.api;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

public class ProvideFeignFallbackFactory{

}


/*@Slf4j
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
}*/
