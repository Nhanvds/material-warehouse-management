package com.demo.mwm.service;

import com.demo.mwm.dto.SupplierDto;

import java.util.List;


public interface ISupplierService {

    SupplierDto getSupplierById(Integer id);

    SupplierDto createSupplier(SupplierDto supplierDto);

    SupplierDto updateSupplier(Integer id, SupplierDto supplierDto);

    void deleteSupplier(Integer id);

    List<SupplierDto> getAllSupplier();
}
