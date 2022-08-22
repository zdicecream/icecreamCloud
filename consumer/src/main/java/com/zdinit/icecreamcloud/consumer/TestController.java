package com.zdinit.icecreamcloud.consumer;

import com.zdinit.icecreamcloud.api.IProvideFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private IProvideFeign provideFeign;

    @RequestMapping(value = "/testConsumer", method = RequestMethod.GET)
    public String testConsumer() {
         return provideFeign.testApi();
    }
}
