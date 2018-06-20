package com.n26.n26.controller;

import com.n26.n26.Services.TransactionService;
import com.n26.n26.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    private TransactionService transactionService;

    TransactionRestController(@Autowired TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction){
        return ResponseEntity.status(transactionService.createTransaction(transaction)).build();
    }
}
