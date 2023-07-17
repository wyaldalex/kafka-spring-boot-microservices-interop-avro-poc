package com.tudux.kafkamicros.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInfoDto {

    private String bankId;
    private String accountOriginNumber;
    private String accountTargetNumber;
    private String senderId;
    private String receiverId;
    private BigDecimal amount;
}
