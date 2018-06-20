package com.n26.n26.Services;

import com.n26.n26.controller.ApiResponse;
import com.n26.n26.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class TransactionService {
    private TreeMap<Long, List<Transaction>> transactions;
    public TransactionService() {
        this.transactions = new TreeMap<>();
    }
    public synchronized TreeMap<Long, List<Transaction>> getAll() {
        return transactions;
    }

    public synchronized Integer createTransaction(Transaction transaction) {
        int status;
        Long currentTimestamp = Instant.now().toEpochMilli();
        Long timeStampMinuteAgo = Instant.now().minusSeconds(60L).toEpochMilli();
        if (transaction.getTimestamp() > currentTimestamp) {
            status = 400; //"Bad request. Date cannot be in the future";
        } else {
            List<Transaction> transactionList = transactions.get(transaction.getTimestamp());
            if (transactionList == null) {
                transactionList = new ArrayList<>();
            }
            transactionList.add(transaction);
            transactions.put(transaction.getTimestamp(), transactionList);
            if (transaction.getTimestamp() < timeStampMinuteAgo) {
                status = 204; //message = "Timestamp older then 60 seconds";
            } else {
                status = 201; //message = "Success";
            }
        }
        return status;
    }
}
