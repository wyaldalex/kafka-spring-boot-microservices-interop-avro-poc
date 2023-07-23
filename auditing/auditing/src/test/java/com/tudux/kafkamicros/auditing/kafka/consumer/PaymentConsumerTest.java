package com.tudux.kafkamicros.auditing.kafka.consumer;

import com.tudux.kafkamicros.auditing.kafka.PaymentConsumer;
import com.tudux.kafkamicros.auditing.service.IAbnormalTransactionService;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentConsumerTest {

    @Mock
    private IAbnormalTransactionService abnormalTransactionService;

    @InjectMocks
    private PaymentConsumer paymentConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListen() {
        // Arrange
        AtomicInteger counter = paymentConsumer.counter; // Access the AtomicInteger from the paymentConsumer instance
        String key = "sample-key";
        PaymentInfoSchema paymentInfo = new PaymentInfoSchema(); // Create your test PaymentInfoSchema object here
        paymentInfo.setId(1l);
        paymentInfo.setReceiverId("1R");
        paymentInfo.setAccountTargetNumber("1TA");
        paymentInfo.setAccountOriginNumber("1OA");
        paymentInfo.setBankId("1B");
        paymentInfo.setAmount("12121.12");
        paymentInfo.setSenderId("1S");



        // Create a mock message with the necessary headers
        Message<PaymentInfoSchema> message = MessageBuilder
                .withPayload(paymentInfo)
                .setHeader(KafkaHeaders.RECEIVED_MESSAGE_KEY, key)
                .build();

        // Mock the IAbnormalTransactionService's behavior
        // You can modify this behavior based on your test scenario
        doNothing().when(abnormalTransactionService).processTransaction(any(PaymentInfoSchema.class));

        // Act
        paymentConsumer.listen(key, message.getPayload());

        // Assert
        // Verify the expected behavior based on your test scenario
        verify(abnormalTransactionService, times(1)).processTransaction(any(PaymentInfoSchema.class));
        // Verify other expected behavior if needed
    }
}
