package com.zdinit.icecreamcloud.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 降级方式有两种fallback 和 FallbackFactory，推荐用FallbackFactory
@FeignClient(name = "provide",fallback = RemoteHystrix.class)
@FeignClient(name = "provide",fallbackFactory = ProvideFeignFallbackFactory.class)

 */
@FeignClient(name = "provide",fallbackFactory = ProvideFeignFallbackFactory.class)
public interface IProvideFeign {
    @RequestMapping(value = "/provideTest",method = RequestMethod.GET)
    String testApi();
}
