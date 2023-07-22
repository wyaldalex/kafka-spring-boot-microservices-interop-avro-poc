package com.tudux.kafkamicros.auditing.controller;

import com.tudux.kafkamicros.auditing.dto.AbnormalTransactionDto;
import com.tudux.kafkamicros.auditing.service.IAbnormalTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/abnormal-transactions")
public class AbnormalTransactionController {

    @Autowired
    IAbnormalTransactionService abnormalTransactionService;

    @GetMapping
    public ResponseEntity<List<AbnormalTransactionDto>> getAllTransactions(){
        return new ResponseEntity<>(abnormalTransactionService.getTransactions(), HttpStatus.OK);
    }

}
