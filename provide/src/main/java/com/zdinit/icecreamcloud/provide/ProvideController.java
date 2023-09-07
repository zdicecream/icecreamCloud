package com.zdinit.icecreamcloud.provide;

import com.zdinit.icecreamcloud.common.kafka.config.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class ProvideController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/provideTest")
    public String provide(){
        log.info("provide is me_");
//        kafkaTemplate.send(Topic.COMMON,"1");
        ListenableFuture<SendResult> future = kafkaTemplate.send(Topic.COMMON,"1");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "1";
    }
}
