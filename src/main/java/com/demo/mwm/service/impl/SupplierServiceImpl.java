package com.demo.mwm.service.impl;


import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.entity.SupplierEntity;
import com.demo.mwm.repository.IMaterialRepository;
import com.demo.mwm.repository.ISupplierRepository;
import com.demo.mwm.service.ISupplierService;
import com.demo.mwm.dto.SupplierDto;
import com.demo.mwm.exception.CustomException;
import com.demo.mwm.service.mapper.ISupplierEntityMapper;
import com.demo.mwm.utils.AESUtils;
import com.demo.mwm.utils.RSAUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private final ISupplierEntityMapper supplierEntityMapper;
    private final ISupplierRepository supplierRepository;
    private final IMaterialRepository materialRepository;
    private final AESUtils aesUtils;
    private final RSAUtils rsaUtils;

    public SupplierServiceImpl(ISupplierEntityMapper supplierEntityMapper,
                               ISupplierRepository supplierRepository,
                               IMaterialRepository materialRepository, AESUtils aesUtils, RSAUtils rsaUtils) {
        this.supplierEntityMapper = supplierEntityMapper;
        this.supplierRepository = supplierRepository;
        this.materialRepository = materialRepository;
        this.aesUtils = aesUtils;
        this.rsaUtils = rsaUtils;
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
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Id null");
        }
        // call procedure
        SupplierEntity supplierEntity = supplierRepository.findSupplierByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found",String.valueOf(id)));
        return supplierEntityMapper.toDto(supplierEntity);
    }


    /**
     * Creates a new supplier entity based on the provided SupplierDto object.
     *
     * @param supplierDto An object containing the information of the new supplier to be created.
     * @return The ID of the newly created supplier entity.
     */
    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        SupplierEntity supplierEntity = supplierEntityMapper.toEntity(supplierDto);
        supplierRepository.save(supplierEntity);
        return supplierEntityMapper.toDto(supplierEntity);
    }

    @Override
    public SupplierDto createSupplierAES(String encryptedData) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JsonProcessingException {
        String decryptedData = aesUtils.decrypt(encryptedData);
        SupplierDto supplierDto = new ObjectMapper().readValue(decryptedData, SupplierDto.class);
        SupplierEntity supplierEntity = supplierEntityMapper.toEntity(supplierDto);
        supplierRepository.save(supplierEntity);
        return supplierEntityMapper.toDto(supplierEntity);
    }

    @Override
    public SupplierDto getSupplierByIdRSA(String encryptedData) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JsonProcessingException {
        String decryptedData = rsaUtils.decrypt(encryptedData);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(Integer.parseInt(decryptedData))
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found","?"));
        return supplierEntityMapper.toDto(supplierEntity);
    }


    /**
     * Updates an existing supplier entity with the provided details.
     * Allows updating various fields of a supplier entity, including:
     * supplierName, supplierCode, supplierAddress, supplierPhoneNumber, supplierNote.
     *
     * @param id          The ID of the supplier to update.
     * @param supplierDto An object containing the updated supplier information.
     * @throws CustomException If no active supplier with the provided ID is found.
     */
    @Override
    public SupplierDto updateSupplier(Integer id, SupplierDto supplierDto) {
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Id null");
        }
        if(Objects.isNull(supplierDto)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Supplier null");
        }
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found", String.valueOf(id)));
        supplierEntityMapper.updateFromDto(supplierEntity, supplierDto);
        supplierRepository.save(supplierEntity);
        return supplierEntityMapper.toDto(supplierEntity);
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
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Id null");
        }
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found",String.valueOf(id)));
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
        return supplierEntityList.stream().map(supplierEntityMapper::toDto).toList();
    }
}
