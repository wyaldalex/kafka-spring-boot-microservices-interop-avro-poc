package com.tudux.kafkamicros.auditing.service;

import com.tudux.kafkamicros.auditing.dto.AbnormalTransactionDto;
import com.tudux.kafkamicros.auditing.entity.AbnormalTransaction;
import com.tudux.kafkamicros.auditing.repository.IAbnormalTransactionsRepository;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class AbnormalTransactionService implements IAbnormalTransactionService{

    private final BigDecimal flagAmount = new BigDecimal("10000");

    @Autowired
    IAbnormalTransactionsRepository repository;

    @Override
    public List<AbnormalTransactionDto> getTransactions() {

        List<AbnormalTransaction> transactions = repository.findAll();
        List<AbnormalTransactionDto> transactionDtos = new LinkedList<AbnormalTransactionDto>();

        for(AbnormalTransaction transaction : transactions) {
            AbnormalTransactionDto dto = AbnormalTransactionDto.builder()
                    .transactionId(transaction.getTransactionId())
                    .amount(transaction.getAmount())
                    .senderId(transaction.getSenderId())
                    .reportedAt(transaction.getReportedAt())
                    .id(transaction.getId())
                    .build();
            transactionDtos.add(dto);
        }

        return transactionDtos;
    }

    @Override
    public void processTransaction(PaymentInfoSchema paymentInfoSchema) {
        String rawAmount = String.valueOf(paymentInfoSchema.getAmount());
        BigDecimal transactionAmount =  new BigDecimal(rawAmount);
        if(transactionAmount.compareTo(flagAmount) == 1 ) {
            log.info("[INFO]: Abnormal transaction detected: " + paymentInfoSchema.getId());

            AbnormalTransaction abnormalTransaction = AbnormalTransaction.builder()
                    .amount(transactionAmount)
                    .transactionId(paymentInfoSchema.getId())
                    .senderId(paymentInfoSchema.getSenderId().toString())
                            .build();
            abnormalTransaction.setReportedAt(new Date());
            repository.save(abnormalTransaction);
        } else {
            log.info("[INFO]: Normal transaction detected: " + paymentInfoSchema.getId());
        }
    }
}
