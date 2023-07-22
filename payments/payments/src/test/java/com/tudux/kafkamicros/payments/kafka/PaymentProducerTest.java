package com.tudux.kafkamicros.payments.kafka;

import com.tudux.kafkamicros.payments.entity.PaymentInfo;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@TestPropertySource(properties = {
        "topics.producer-topic=test-topic" // Set the value for testing
})
public class PaymentProducerTest {

//    @MockBean
//    KafkaTemplate<String, PaymentInfoSchema> kafkaTemplate;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        System.setProperty("topics.producer-topic", "test-topic");
    }

    @Test
    public void testSendPayment() throws Exception {

        KafkaTemplate<String,PaymentInfoSchema> templateMock = mock(KafkaTemplate.class);

        // Using args constructor
        PaymentProducer paymentProducer = new PaymentProducer(templateMock);
        // Arrange
        PaymentInfo payment = new PaymentInfo();
        // Set payment properties here
        payment.setId(1L);
        payment.setAmount(new BigDecimal("12121.12"));
        payment.setBankId("1A");
        payment.setReceiverId("AAA");
        payment.setSenderId("BBB");
        payment.setAccountOriginNumber("AO");
        payment.setAccountTargetNumber("AT");


        // Create a PaymentInfoSchema instance to be sent
        PaymentInfoSchema expectedPayload = new PaymentInfoSchema();
        expectedPayload.setAmount(payment.toString());
        expectedPayload.setAccountOriginNumber(payment.getAccountOriginNumber());
        expectedPayload.setAccountTargetNumber(payment.getAccountTargetNumber());
        expectedPayload.setBankId(payment.getBankId());
        expectedPayload.setSenderId(payment.toString());
        expectedPayload.setReceiverId(payment.toString());
        expectedPayload.setAmount(payment.toString());

        ReflectionTestUtils.setField(paymentProducer, "topic", "test-topic");
        // Create a mock RecordMetadata to simulate the future result
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("test-topic",1), 0, 0, 0, 1, 1);

        // Create a mock SendResult that returns the mock RecordMetadata
        SendResult<String, PaymentInfoSchema> sendResult = mock(SendResult.class);
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);

        // Create a mock ListenableFuture and set the result
        SettableListenableFuture<SendResult<String, PaymentInfoSchema>> future = new SettableListenableFuture<>();
        future.set(sendResult);

        // Mock the send method of the KafkaTemplate to return the mock CompletableFuture
        when(templateMock.send(any(ProducerRecord.class))).thenReturn(future);

        paymentProducer.sendPayment(payment);
        verify(templateMock).send(any(ProducerRecord.class));
//
//
//
//        // Act
//        paymentProducer.sendPayment(payment);
//
//        // Assert
//        verify(templateMock).send(any(ProducerRecord.class));
        // Add more assertions as needed to verify the behavior of your producer

    }
}
