package com.tudux.kafkamicros.auditing.service;


import com.tudux.kafkamicros.auditing.dto.AbnormalTransactionDto;
import com.tudux.kafkamicros.schema.PaymentInfoSchema;

import java.util.List;

public interface IAbnormalTransactionService {

    public List<AbnormalTransactionDto> getTransactions ();
    public void processTransaction (PaymentInfoSchema paymentInfoSchema);
}
