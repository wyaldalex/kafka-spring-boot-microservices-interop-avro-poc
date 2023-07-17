package com.tudux.kafkamicros.payments.controller;

import com.tudux.kafkamicros.payments.dto.PaymentInfoDto;
import com.tudux.kafkamicros.payments.service.IPaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentInfoController {

    @Autowired
    IPaymentInfoService paymentInfoService;

    @PostMapping
    public ResponseEntity<Long> processPaymentInfo(@RequestBody PaymentInfoDto paymentInfo){
        long idResponse = paymentInfoService.processPaymentInfo(paymentInfo);
        return new ResponseEntity<>(idResponse, HttpStatus.CREATED);
    }
}
