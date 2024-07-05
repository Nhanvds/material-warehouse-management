package com.vsii.mwm.service;

import com.vsii.mwm.service.dto.SupplierDto;

import java.util.List;


public interface ISupplierService {

    SupplierDto getSupplierById(Integer id);

    SupplierDto createSupplier(SupplierDto supplierDto);

    SupplierDto updateSupplier(Integer id, SupplierDto supplierDto);

    void deleteSupplier(Integer id);

    List<SupplierDto> getAllSupplier();
}
