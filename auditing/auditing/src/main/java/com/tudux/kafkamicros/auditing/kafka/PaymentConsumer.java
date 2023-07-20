package com.tudux.kafkamicros.auditing.kafka;

import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    final AtomicInteger counter = new AtomicInteger();
    //final PaymentService demoService;
    private String topic;

    @Value("${topics.consumer-topic}")
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

//    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);
//
//    @KafkaListener(topics = "${topics.consumer-topic}") // Use the property directly here
//    public void consumeAvroMessage(ConsumerRecord<String, PaymentInfoSchema> record) {
//        // Process the Avro message (User object)
//        PaymentInfoSchema paymentInfo = record.value();
//        LOGGER.info("Received Avro message: " + paymentInfo.toString());
//    }

    @KafkaListener(topics = "${topics.consumer-topic}", groupId = "demo-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload final PaymentInfoSchema command) {
        counter.getAndIncrement();
        log.info("Received message [" +counter.get()+ "] - key: " + key);
        try {
            //demoService.process(key, command);
            log.info(command.toString());
        } catch (Exception e) {
            log.error("Error processing message: " + e.getMessage());
        }
    }
}
