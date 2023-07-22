package com.tudux.kafkamicros.auditing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalTransactionDto {
    Long id;
    Long transactionId;
    String senderId;
    BigDecimal amount;
    private Date reportedAt;
}
