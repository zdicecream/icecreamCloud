package com.zdinit.icecreamcloud.dispatch.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Slf4j
public class ServerRunner implements CommandLineRunner {

    @Async//注意这里，组件启动时会执行run，这个注解是让线程异步执行，这样不影响主线程
    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void start(){
//        NettyServer nettyServer = new NettyServer();
//        log.info("Netty接口服务端启动");
//        nettyServer.start();
    }
}
