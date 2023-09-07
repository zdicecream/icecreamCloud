package com.zdinit.icecreamcloud.consumer;

import com.zdinit.icecreamcloud.common.kafka.config.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {
    @KafkaListener(topics = Topic.COMMON)
    public void testKafka(){
        log.info("sss");
    }
}
