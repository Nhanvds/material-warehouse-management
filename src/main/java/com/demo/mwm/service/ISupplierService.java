package com.demo.mwm.service;

import com.demo.mwm.dto.PageDto;
import com.demo.mwm.dto.SupplierDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface ISupplierService {

    SupplierDto getSupplierById(Integer id);

    SupplierDto createSupplier(SupplierDto supplierDto);

    SupplierDto createSupplierAES(String encryptedData) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JsonProcessingException;

    SupplierDto getSupplierByIdRSA(String encryptedData) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JsonProcessingException;
    SupplierDto updateSupplier(Integer id, SupplierDto supplierDto);

    void deleteSupplier(Integer id);

    List<SupplierDto> getAllSupplier();
}
