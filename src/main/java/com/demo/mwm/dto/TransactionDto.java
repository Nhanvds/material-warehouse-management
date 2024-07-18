package com.demo.mwm.dto;

import java.time.LocalDate;

public class TransactionDto {
    private Integer id;
    private String transactionId;
    private String account;
    private Double indebted;
    private Double have;
    private LocalDate time;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
