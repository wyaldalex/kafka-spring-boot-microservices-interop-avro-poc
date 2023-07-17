package com.tudux.kafkamicros.payments.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInfoPayload {

    private long id;
    private String bankId;
    private String accountOriginNumber;
    private String accountTargetNumber;
    private String senderId;
    private String receiverId;
    private String amount;
}
