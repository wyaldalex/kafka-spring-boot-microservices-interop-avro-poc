package com.tudux.kafkamicros.payments.service;

import com.tudux.kafkamicros.payments.dto.PaymentInfoDto;
import com.tudux.kafkamicros.payments.entity.PaymentInfo;
import com.tudux.kafkamicros.payments.kafka.PaymentProducer;
import com.tudux.kafkamicros.payments.repository.IPaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoService implements IPaymentInfoService {


    @Autowired
    IPaymentInfoRepository repository;

    @Autowired
    PaymentProducer paymentProducer;

    @Override
    public long processPaymentInfo(PaymentInfoDto dto) {
        // process payment information and push data to db
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .bankId(dto.getBankId())
                .accountOriginNumber(dto.getAccountOriginNumber())
                .accountTargetNumber(dto.getAccountTargetNumber())
                .receiverId(dto.getReceiverId())
                .senderId(dto.getSenderId())
                .bankId(dto.getBankId())
                .amount(dto.getAmount())
                .build();

        PaymentInfo paymentInfoRes = repository.save(paymentInfo);
        // update auditing topic
        paymentProducer.sendPayment(paymentInfoRes);

        return paymentInfoRes.getId();
    }
}
