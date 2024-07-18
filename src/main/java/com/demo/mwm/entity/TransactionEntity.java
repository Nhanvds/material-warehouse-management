package com.demo.mwm.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account")
    private String account;

    @Column(name = "indebted")
    private Double indebted;

    @Column(name = "have")
    private Double have;

    @Column(name = "time")
    private LocalDate time;

    public Integer getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getIndebted() {
        return indebted;
    }

    public void setIndebted(Double indebted) {
        this.indebted = indebted;
    }

    public Double getHave() {
        return have;
    }

    public void setHave(Double have) {
        this.have = have;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
