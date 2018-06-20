package com.n26.n26.component;


import com.n26.n26.Services.TransactionService;
import com.n26.n26.model.Statistics;
import com.n26.n26.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import java.util.stream.Collectors;

@Component

public class StatisticsComponent {
    @Autowired
    TransactionService transactionService;
    @Autowired
    AggregatorComponent aggregatorComponent;

    public StatisticsComponent getAllStatisticComponents() {
         List<Transaction> transactions = this.transactionService.getAll().tailMap(Instant.now().minusSeconds(60L).toEpochMilli())
                 .values().stream()
                 .flatMap(t -> t.stream())
                 .collect(Collectors.toList());
        this.aggregatorComponent.aggregate(transactions);
        return this;
    }

    public Statistics getStatistic() {
        return new Statistics(aggregatorComponent.getSum(), aggregatorComponent.getAvg(),
                aggregatorComponent.getMax(), aggregatorComponent.getMin(), aggregatorComponent.getCount());
    }
 }
