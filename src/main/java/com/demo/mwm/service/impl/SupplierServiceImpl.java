package com.demo.mwm.service.impl;

import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.entity.SupplierEntity;
import com.demo.mwm.repository.IMaterialRepository;
import com.demo.mwm.repository.ISupplierRepository;
import com.demo.mwm.service.ISupplierService;
import com.demo.mwm.dto.SupplierDto;
import com.demo.mwm.service.exception.CustomException;
import com.demo.mwm.service.mapper.ISupplierEntityMapper;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private final ISupplierEntityMapper supplierEntityMapper;
    private final ISupplierRepository supplierRepository;
    private final IMaterialRepository materialRepository;
    private final EntityManager entityManager;

    public SupplierServiceImpl(ISupplierEntityMapper supplierEntityMapper,
                               ISupplierRepository supplierRepository,
                               IMaterialRepository materialRepository, EntityManager entityManager) {
        this.supplierEntityMapper = supplierEntityMapper;
        this.supplierRepository = supplierRepository;
        this.materialRepository = materialRepository;
        this.entityManager = entityManager;
    }


    /**
     * Get a supplier by id
     *
     * @param id The supplier ID needs getting.
     * @return SupplierDto
     * @throws CustomException If a supplier with the provided ID is not found.
     */
    @Override
    @Transactional
    public SupplierDto getSupplierById(Integer id) {
//        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
//                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Supplier not found"));
        // call procedure
        SupplierEntity supplierEntity = supplierRepository.findSupplierByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        return supplierEntityMapper.toDto(supplierEntity);
    }


    /**
     * Creates a new supplier.
     *
     * @param supplierDto An object containing the new supplier information.
     * @return A SupplierDto object representing the created supplier.
     */
    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierEntityMapper.toEntity(supplierDto);
        return supplierEntityMapper.toDto(supplierRepository.save(supplierEntity));
    }


    /**
     * Updates a supplier with the provided details.
     * This method allows updating various fields of a supplier entity, including:
     * update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote
     *
     * @param id          The ID of the supplier to update.
     * @param supplierDto An object containing the updated supplier information.
     * @return A SupplierDto object representing the updated supplier.
     * @throws CustomException If a supplier with the provided ID is not found or inactive.
     */
    @Override
    public SupplierDto updateSupplier(Integer id, SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        supplierEntityMapper.updateFromDto(supplierEntity, supplierDto);
        return supplierEntityMapper.toDto(supplierRepository.save(supplierEntity));
    }

    /**
     * Deletes a supplier by marking it inactive.
     * This method overrides the `deleteSupplier` method and performs a soft delete by setting
     * the `isActive` flag of the supplier and its associated materials to `false`.
     *
     * @param id The ID of the supplier to delete.
     * @throws CustomException If a supplier with the provided ID is not found.
     */
    @Override
    public void deleteSupplier(Integer id) {
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        supplierEntity.setActive(false);
        List<MaterialEntity> materialEntities = materialRepository.findAllByIsActiveTrueAndSupplierId(id);
        materialEntities.forEach(materialEntity -> materialEntity.setActive(false));
        supplierRepository.save(supplierEntity);
        materialRepository.saveAll(materialEntities);
    }

    /**
     * @return All suppliers List<SupplierDto>
     */
    @Override
    public List<SupplierDto> getAllSupplier() {
        List<SupplierEntity> supplierEntityList = supplierRepository.getAllByIsActiveTrue();
        return supplierEntityList.stream()
                .map(supplierEntityMapper::toDto)
                .toList();
    }
}
