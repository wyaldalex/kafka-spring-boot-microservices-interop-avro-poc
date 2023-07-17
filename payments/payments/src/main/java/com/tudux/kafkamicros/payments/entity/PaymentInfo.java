package com.tudux.kafkamicros.payments.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

// simple class for poc kafka micro communication
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bankId;
    private String accountOriginNumber;
    private String accountTargetNumber;
    private String senderId;
    private String receiverId;
    private BigDecimal amount;

}
