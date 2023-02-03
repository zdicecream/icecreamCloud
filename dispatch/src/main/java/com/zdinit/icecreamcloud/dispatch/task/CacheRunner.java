package com.zdinit.icecreamcloud.dispatch.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
public class CacheRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
/*        CdRunstate runstate = runstateService.getOne(null);
        redisUtil.set("bankname",runstate.getRsBankname());
        redisUtil.set("apid",runstate.getRsApid());
        redisUtil.set("loginstate",runstate.getRsLogonstate());
        redisUtil.set("date",runstate.getRsCurdate().toString());
        redisUtil.set("state",runstate.getRsSysstate());*/
    }
}
