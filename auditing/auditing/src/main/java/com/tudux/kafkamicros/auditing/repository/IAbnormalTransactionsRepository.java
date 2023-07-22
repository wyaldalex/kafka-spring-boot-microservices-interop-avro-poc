package com.tudux.kafkamicros.auditing.repository;

import com.tudux.kafkamicros.auditing.entity.AbnormalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbnormalTransactionsRepository extends JpaRepository<AbnormalTransaction, Long> {

}
