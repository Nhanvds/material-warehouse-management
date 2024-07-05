package com.vsii.mwm.service.impl;

import com.vsii.mwm.domain.MaterialEntity;
import com.vsii.mwm.domain.SupplierEntity;
import com.vsii.mwm.repository.IMaterialRepository;
import com.vsii.mwm.repository.ISupplierRepository;
import com.vsii.mwm.service.ISupplierService;
import com.vsii.mwm.service.dto.SupplierDto;
import com.vsii.mwm.service.exception.CustomException;
import com.vsii.mwm.service.mapper.ISupplierEntityMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private final ISupplierEntityMapper supplierEntityMapper;
    private final ISupplierRepository supplierRepository;
    private final IMaterialRepository materialRepository;

    public SupplierServiceImpl(ISupplierEntityMapper supplierEntityMapper,
                               ISupplierRepository supplierRepository,
                               IMaterialRepository materialRepository) {
        this.supplierEntityMapper = supplierEntityMapper;
        this.supplierRepository = supplierRepository;
        this.materialRepository = materialRepository;
    }


    @Override
    public SupplierDto getSupplierById(Integer id) {
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        return supplierEntityMapper.toDto(supplierEntity);
    }

    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierEntityMapper.toEntity(supplierDto);
        return supplierEntityMapper.toDto(supplierRepository.save(supplierEntity));
    }

    @Override
    public SupplierDto updateSupplier(Integer id, SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        supplierEntityMapper.updateFromDto(supplierEntity, supplierDto);
        return supplierEntityMapper.toDto(supplierRepository.save(supplierEntity));
    }

    @Override
    public void deleteSupplier(Integer id) {
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        supplierEntity.setActive(false);
        List<MaterialEntity> materialEntities = materialRepository.findAllByIsActiveTrueAndSupplierId(id);
        materialEntities.forEach(materialEntity -> materialEntity.setActive(false));
        supplierRepository.save(supplierEntity);
        materialRepository.saveAll(materialEntities);
    }

    @Override
    public List<SupplierDto> getAllSupplier() {
        List<SupplierEntity> supplierEntityList = supplierRepository.getAllByIsActiveTrue();
        return supplierEntityList.stream()
                .map(supplierEntityMapper::toDto)
                .toList();
    }
}
