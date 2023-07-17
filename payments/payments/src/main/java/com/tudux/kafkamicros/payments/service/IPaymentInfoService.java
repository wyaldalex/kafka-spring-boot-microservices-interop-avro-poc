package com.tudux.kafkamicros.payments.service;

import com.tudux.kafkamicros.payments.dto.PaymentInfoDto;

public interface IPaymentInfoService {

    public long  processPaymentInfo(PaymentInfoDto paymentInfoDto);
}
