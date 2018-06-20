package com.n26.n26.component;

import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import com.n26.n26.model.Transaction;
@Component
public class AggregatorComponent {
    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
    public synchronized void aggregate(List<Transaction> transaction) {
        if(transaction == null || transaction.isEmpty()) {
            this.avg = 0.0;
            this.avg = 0.0;
            this.sum = 0.0;
            this.max = 0.0;
            this.min = 0.0;
            this.count = 0L;
            return;
        } else {
            DoubleSummaryStatistics doubleSummaryStatistics = transaction.parallelStream().collect(Collectors.summarizingDouble(Transaction::getAmount));
            this.avg = doubleSummaryStatistics.getAverage();
            this.sum = doubleSummaryStatistics.getSum();
            this.max = doubleSummaryStatistics.getMax();
            this.min = doubleSummaryStatistics.getMin();
            this.count = doubleSummaryStatistics.getCount();
        }
    }

    public Double getSum() {
        return sum;
    }
    public Double getAvg() {
        return avg;
    }
    public Double getMax() {
        return max;
    }
    public Double getMin() {
        return min;
    }
    public Long getCount() {
        return count;
    }

}
