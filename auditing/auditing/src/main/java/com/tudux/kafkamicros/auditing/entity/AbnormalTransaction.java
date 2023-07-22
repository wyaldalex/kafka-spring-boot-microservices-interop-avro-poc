package com.tudux.kafkamicros.auditing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbnormalTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long transactionId;
    String senderId;
    BigDecimal amount;
    @CreatedDate
    @Column(name = "reported_at", nullable = false, updatable = false)
    private Date reportedAt;
}
