package com.demo.mwm.service;

import com.demo.mwm.dto.TransactionDto;

public interface ITransactionService {

    TransactionDto createTransaction(String transactionId, String account, String indebted, String have, String time);
}
