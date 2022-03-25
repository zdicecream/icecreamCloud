package com.zdinit.icecreamcloud.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class TestController {
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public String one(){
        log.info("api one");
        return "one";
    }

    @RequestMapping(value = "/two", method = RequestMethod.GET)
    public String two(){
        log.info("api two");
        return "two";
    }
}
