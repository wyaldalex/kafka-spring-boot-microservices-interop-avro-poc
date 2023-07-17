package com.tudux.kafkamicros.payments.kafka;

import com.tudux.kafkamicros.payments.entity.PaymentInfo;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    @Value("${topics.producer-topic}")
    private String topic;

    private final KafkaTemplate<String, PaymentInfoSchema> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, PaymentInfoSchema> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

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

        kafkaTemplate.send(topic, payload);
    }
}