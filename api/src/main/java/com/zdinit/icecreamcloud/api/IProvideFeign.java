package com.zdinit.icecreamcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "provide",fallback = RemoteHystrix.class)
public interface IProvideFeign {
    @RequestMapping(value = "/provideTest",method = RequestMethod.GET)
    void testApi();
}
