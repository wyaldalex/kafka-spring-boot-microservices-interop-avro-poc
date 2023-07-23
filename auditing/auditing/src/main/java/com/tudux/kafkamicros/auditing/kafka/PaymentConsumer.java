package com.tudux.kafkamicros.auditing.kafka;

import com.tudux.kafkamicros.auditing.service.IAbnormalTransactionService;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {


    public final AtomicInteger counter = new AtomicInteger();
    private String topic;

    @Autowired
    private IAbnormalTransactionService abnormalTransactionService;

    @Value("${topics.consumer-topic}")
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @KafkaListener(topics = "${topics.consumer-topic}", groupId = "demo-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload final PaymentInfoSchema command) {
        counter.getAndIncrement();
        log.info("Received message [" +counter.get()+ "] - key: " + key);
        try {
            abnormalTransactionService.processTransaction(command);
            log.info("[INFO] Successfully processed abnormal transaction:" + command.toString());
        } catch (Exception e) {
            log.error("Error processing message: " + e.getMessage());
        }
    }
}
