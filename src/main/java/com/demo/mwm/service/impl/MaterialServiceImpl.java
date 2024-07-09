package com.demo.mwm.service.impl;

import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.entity.SupplierEntity;
import com.demo.mwm.repository.IMaterialRepository;
import com.demo.mwm.repository.ISupplierRepository;
import com.demo.mwm.repository.impl.MaterialSpecification;
import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.dto.MaterialDto;
import com.demo.mwm.dto.response.PageResponse;
import com.demo.mwm.service.exception.CustomException;
import com.demo.mwm.service.mapper.IMaterialEntityMapper;
import com.demo.mwm.service.mapper.ISupplierEntityMapper;
import com.demo.mwm.utils.Constants;
import com.demo.mwm.utils.Utils;
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

    /**
     * Retrieves the details of a material by its ID.
     *
     * @param id The ID of the material to retrieve.
     * @return A MaterialDto object containing the details of the material and its associated supplier.
     * @throws CustomException If a material or supplier with the provided IDs is not found or inactive.
     */
    @Override
    public MaterialDto getDetailMaterialById(Integer id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Material not found"));
        MaterialDto materialDto = materialEntityMapper.toDto(materialEntity);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialEntity.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        materialDto.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return materialDto;
    }

    /**
     * Creates a new material.
     *
     * @param materialDto An object containing the new material information.
     * @return A MaterialDto object representing the created material.
     * @throws CustomException If a supplier with the provided ID is not found or inactive.
     */
    @Override
    public MaterialDto createMaterial(MaterialDto materialDto) {
        MaterialEntity materialEntity = materialEntityMapper.toEntity(materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        materialEntity.setSupplierId(supplierEntity.getId());
        materialRepository.save(materialEntity);
        MaterialDto savedMaterial = materialEntityMapper.toDto(materialEntity);
        savedMaterial.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return savedMaterial;
    }

    /**
     * Updates a material with the provided details.
     * This method allows updating various fields of a material entity, including:
     * - materialCode
     * - materialName
     * - materialPrice
     * - materialQuantity
     * - materialNote
     * - supplierId
     *
     * @param id          The ID of the material to update.
     * @param materialDto An object containing the updated material information.
     * @return A MaterialDto object representing the updated material.
     * @throws CustomException If a material or supplier with the provided IDs is not found.
     */
    @Override
    public MaterialDto updateMaterial(Integer id, MaterialDto materialDto) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Material not found"));
        materialEntityMapper.updateFromDto(materialEntity, materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Supplier not found"));
        materialEntity.setSupplierId(supplierEntity.getId());
        materialRepository.save(materialEntity);
        MaterialDto updatedMaterial = materialEntityMapper.toDto(materialEntity);
        updatedMaterial.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return updatedMaterial;
    }

    /**
     * Deletes a material by marking it inactive.
     *
     * @param id The ID of the material to delete.
     * @throws CustomException If a material with the provided ID is not found.
     */
    @Override
    public void deleteMaterial(Integer id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Material not found"));
        materialEntity.setActive(false);
        materialRepository.save(materialEntity);
    }

    /**
     * @param page          :Page number for pagination, default is 0. If 0, returns all materials.
     * @param size          :Page size for pagination, default is 0. If 0, returns all materials.
     * @param sortProperty: Property to sort by, optional.
     * @param sortOrder:    Order of sorting, either 'asc' or 'desc', optional.
     * @param materialName: Filter materials by name, optional.
     * @param materialCode: Filter materials by code, optional.
     * @return PageResponse<List < MaterialDto> a paginated list of materials. If page and size are not provided, returns all materials.
     */
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
