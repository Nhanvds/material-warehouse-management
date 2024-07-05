package com.demo.mwm.service.impl;

import com.demo.mwm.domain.MaterialEntity;
import com.demo.mwm.domain.SupplierEntity;
import com.demo.mwm.repository.IMaterialRepository;
import com.demo.mwm.repository.ISupplierRepository;
import com.demo.mwm.repository.specification.MaterialSpecification;
import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.service.dto.MaterialDto;
import com.demo.mwm.service.dto.response.PageResponse;
import com.demo.mwm.service.exception.CustomException;
import com.demo.mwm.service.mapper.IMaterialEntityMapper;
import com.demo.mwm.service.mapper.ISupplierEntityMapper;
import com.demo.mwm.service.utils.Constants;
import com.demo.mwm.service.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements IMaterialService {

    private final IMaterialEntityMapper materialEntityMapper;
    private final ISupplierEntityMapper supplierEntityMapper;
    private final IMaterialRepository materialRepository;
    private final ISupplierRepository supplierRepository;

    public MaterialServiceImpl(IMaterialEntityMapper materialEntityMapper,
                               ISupplierEntityMapper supplierEntityMapper, IMaterialRepository materialRepository,
                               ISupplierRepository supplierRepository) {
        this.materialEntityMapper = materialEntityMapper;
        this.supplierEntityMapper = supplierEntityMapper;
        this.materialRepository = materialRepository;
        this.supplierRepository = supplierRepository;

    }


    @Override
    public MaterialDto getDetailMaterialById(Integer id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Material not found", HttpStatus.NOT_FOUND));
        MaterialDto materialDto = materialEntityMapper.toDto(materialEntity);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialEntity.getSupplierId())
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        materialDto.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return materialDto;
    }

    @Override
    public MaterialDto createMaterial(MaterialDto materialDto) {
        MaterialEntity materialEntity = materialEntityMapper.toEntity(materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        materialEntity.setSupplierId(supplierEntity.getId());
        materialRepository.save(materialEntity);
        return materialEntityMapper.toDto(materialEntity);
    }

    @Override
    public MaterialDto updateMaterial(Integer id, MaterialDto materialDto) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Material not found", HttpStatus.NOT_FOUND));
        materialEntityMapper.updateFromDto(materialEntity, materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException("Supplier not found", HttpStatus.NOT_FOUND));
        materialEntity.setSupplierId(supplierEntity.getId());
        return materialEntityMapper.toDto(materialRepository.save(materialEntity));
    }

    @Override
    public void deleteMaterial(Integer id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Material not found", HttpStatus.NOT_FOUND));
        materialEntity.setActive(false);
        materialRepository.save(materialEntity);
    }

    @Override
    public PageResponse<?> getMaterialList(Integer page, Integer size, String sortProperty, String sortOrder, String materialName, String materialCode) {
        Pageable pageable = Utils.getPageable(page, size);
        Specification<MaterialEntity> specification = Specification
                .where(MaterialSpecification.isActive(Constants.IS_ACTIVE_TRUE)
                        .and(MaterialSpecification.hasMaterialNameLike(materialName))
                        .and(MaterialSpecification.hasMaterialCodeLike(materialCode)))
                .and(MaterialSpecification.sort(sortProperty, sortOrder));
        Page<MaterialEntity> entityPage = materialRepository.findAll(specification, pageable);

        return new PageResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(entityPage.stream().map(materialEntityMapper::toDto).toList())
                .dataCount(entityPage.getTotalElements());

    }
}
