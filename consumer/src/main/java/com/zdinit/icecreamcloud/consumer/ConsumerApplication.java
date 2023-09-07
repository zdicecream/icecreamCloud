package com.zdinit.icecreamcloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zdinit.icecreamcloud.consumer"})
@EnableKafka
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
