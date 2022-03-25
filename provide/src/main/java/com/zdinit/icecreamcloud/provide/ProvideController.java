package com.zdinit.icecreamcloud.provide;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class ProvideController {

    @GetMapping("/provideTest")
    public void provide(){
        log.info("provide is me_");
    }
}
