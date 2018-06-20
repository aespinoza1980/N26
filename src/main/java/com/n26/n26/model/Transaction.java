package com.n26.n26.model;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    private Double amount;
    private Long timestamp;

    public Transaction(Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public Double getAmount() {
        return amount;
    }
}

