package com.tudux.kafkamicros.payments.repository;

import com.tudux.kafkamicros.payments.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentInfoRepository extends JpaRepository<PaymentInfo,Long> {
}
