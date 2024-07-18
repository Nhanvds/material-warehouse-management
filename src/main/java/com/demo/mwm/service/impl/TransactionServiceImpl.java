package com.demo.mwm.service.impl;

import com.demo.mwm.dto.TransactionDto;
import com.demo.mwm.entity.TransactionEntity;
import com.demo.mwm.exception.CustomException;
import com.demo.mwm.repository.ITransactionRepository;
import com.demo.mwm.service.ITransactionService;
import com.demo.mwm.service.mapper.ITransactionEntityMapper;
import com.demo.mwm.utils.AESUtils;
import com.demo.mwm.utils.Constants;
import com.demo.mwm.utils.RSAUtils;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final ITransactionEntityMapper transactionEntityMapper;
    private final AESUtils aesUtils;
    private final RSAUtils rsaUtils;

    public TransactionServiceImpl(ITransactionRepository transactionRepository, ITransactionEntityMapper transactionEntityMapper, AESUtils aesUtils, RSAUtils rsaUtils) {
        this.transactionRepository = transactionRepository;
        this.transactionEntityMapper = transactionEntityMapper;
        this.aesUtils = aesUtils;
        this.rsaUtils = rsaUtils;
    }

    @Override
    public TransactionDto createTransaction(String transactionId,
                                            String account,
                                            String indebted, String have, String time) {
        if (transactionId == null) {
            throw new CustomException("TransactionId is null");
        }
        if (account == null) {
            throw new CustomException("Account is null");
        }
        if (indebted == null) {
            throw new CustomException("Indebted is null");
        }
        if (have == null) {
            throw new CustomException("Have is null");
        }
        if (time == null) {
            throw new CustomException("Time is null");
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(rsaUtils.decrypt(transactionId));
        transactionEntity.setAccount(aesUtils.encrypt(rsaUtils.decrypt(account)));
        try{
            transactionEntity.setTime(LocalDate.parse(rsaUtils.decrypt(time), Constants.CUSTOM_DATE_FORMATTER));
        }catch (DateTimeParseException exception){
            throw new CustomException("Error parsing time value");
        }
        try{
            transactionEntity.setHave(Double.parseDouble(rsaUtils.decrypt(have)));
            transactionEntity.setIndebted(Double.parseDouble(rsaUtils.decrypt(indebted)));
        }catch (NumberFormatException ignored ){
            throw new CustomException("Error parsing double value");
        }
        transactionRepository.save(transactionEntity);
        return transactionEntityMapper.toDto(transactionEntity);
    }
}
