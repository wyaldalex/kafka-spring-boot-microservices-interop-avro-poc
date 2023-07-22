package com.tudux.kafkamicros.payments.kafka;

import com.tudux.kafkamicros.payments.entity.PaymentInfo;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentProducer {

    @Value("${topics.producer-topic}")
    private String topic;

    private final KafkaTemplate<String, PaymentInfoSchema> kafkaTemplate;

//    public PaymentProducer(KafkaTemplate<String, PaymentInfoSchema> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    public void sendPayment(PaymentInfo payment) {

        //Avro serialization for BigDecimal not out of the box, may require changes
        PaymentInfoSchema payload =  new PaymentInfoSchema();
        payload.setAmount(payment.toString());
        payload.setAccountOriginNumber(payment.getAccountOriginNumber());
        payload.setAccountTargetNumber(payment.getAccountTargetNumber());
        payload.setBankId(payment.getBankId());
        payload.setSenderId(payment.toString());
        payload.setReceiverId(payment.toString());
        payload.setAmount(payment.toString());

        try {
            String messageId = UUID.randomUUID().toString();
            final ProducerRecord<String, PaymentInfoSchema> record = new ProducerRecord<>(topic, messageId, payload);
            final SendResult result = (SendResult) kafkaTemplate.send(record).get();
            final RecordMetadata metadata = result.getRecordMetadata();
            log.debug(String.format("Sent record(key=%s value=%s) meta(topic=%s, partition=%d, offset=%d)",
                    record.key(), record.value(), metadata.topic(), metadata.partition(), metadata.offset()));
            log.info("Record successfully pushed to topic " + result);
        } catch (Exception e) {
            String message = "Error sending message to topic " + topic;
            log.error(message);
            throw new RuntimeException(message, e);
        }
        //kafkaTemplate.send(topic, payload);
    }
}