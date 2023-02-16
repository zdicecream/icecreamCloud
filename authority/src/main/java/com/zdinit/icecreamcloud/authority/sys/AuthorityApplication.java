package com.zdinit.icecreamcloud.authority.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.zdinit.icecreamcloud.authority","com.zdinit.icecreamcloud.common"})
//@MapperScan(basePackages = "com.zdinit.icecreamcloud.authority.**.mapper")
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }

}
