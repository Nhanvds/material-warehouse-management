package com.demo.mwm.service.impl;

import com.demo.mwm.dto.PageDto;
import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.entity.SupplierEntity;
import com.demo.mwm.repository.IMaterialRepository;
import com.demo.mwm.repository.ISupplierRepository;
import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.dto.MaterialDto;
import com.demo.mwm.exception.CustomException;
import com.demo.mwm.service.mapper.IMaterialEntityMapper;
import com.demo.mwm.service.mapper.ISupplierEntityMapper;
import com.demo.mwm.utils.Constants;
import com.demo.mwm.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
     * Retrieves the details of a material entity by its ID.
     * Retrieves both the material details and its associated supplier details.
     *
     * @param id The ID of the material entity to retrieve.
     * @return A MaterialDto object containing the details of the material and its associated supplier.
     * @throws CustomException If no active material or supplier with the provided IDs is found.
     */
    @Override
    public MaterialDto getDetailMaterialById(Integer id) {
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Id null");
        }
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.material.not.found", String.valueOf(id)));
        MaterialDto materialDto = materialEntityMapper.toDto(materialEntity);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialEntity.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found", String.valueOf(materialEntity.getSupplierId())));
        materialDto.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return materialDto;
    }


    /**
     * Creates a new material.
     *
     * @param materialDto An object containing the new material information.
     * @return The ID of the newly created material entity.
     * @throws CustomException If a supplier with the provided ID is not found or inactive.
     */
    @Override
    public MaterialDto createMaterial(MaterialDto materialDto) {
        if(Objects.isNull(materialDto)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"MaterialDto null");
        }
        MaterialEntity materialEntity = materialEntityMapper.toEntity(materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found", String.valueOf(materialDto.getSupplierId())));
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
     * @return MaterialDto saved
     * @throws CustomException If a material or supplier with the provided IDs is not found.
     */
    @Override
    public MaterialDto updateMaterial(Integer id, MaterialDto materialDto) {
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Id null");
        }
        if(Objects.isNull(materialDto)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"MaterialDto null");
        }
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.material.not.found", String.valueOf(id)));
        materialEntityMapper.updateFromDto(materialEntity, materialDto);
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsActiveTrue(materialDto.getSupplierId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.supplier.not.found", String.valueOf(materialDto.getSupplierId())));
        materialEntity.setSupplierId(supplierEntity.getId());
        materialRepository.save(materialEntity);
        MaterialDto updatedMaterialDto = materialEntityMapper.toDto(materialEntity);
        updatedMaterialDto.setSupplier(supplierEntityMapper.toDto(supplierEntity));
        return updatedMaterialDto;
    }

    /**
     * Deletes a material by marking it inactive.
     * @param id The ID of the material to delete.
     * @throws CustomException If a material with the provided ID is not found.
     */
    @Override
    public void deleteMaterial(Integer id) {
        if(Objects.isNull(id)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"id null");
        }
        MaterialEntity materialEntity = materialRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "error.material.not.found", String.valueOf(id)));
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
     * @return PageDto < MaterialDto> a paginated list of materials. If page and size are not provided, returns all materials.
     */
    @Override
    public PageDto<MaterialDto> getMaterialList(Integer page, Integer size, String sortProperty, String sortOrder, String materialName, String materialCode) {
        Pageable pageable = Utils.getPageable(page, size);
        Specification<MaterialEntity> specification = Specification
                .where(IMaterialRepository.isActive(Constants.IS_ACTIVE_TRUE)
                        .and(IMaterialRepository.hasMaterialNameLike(materialName))
                        .and(IMaterialRepository.hasMaterialCodeLike(materialCode)))
                .and(IMaterialRepository.sort(sortProperty, sortOrder));
        Page<MaterialEntity> entityPage = materialRepository.findAll(specification, pageable);
        return new PageDto<MaterialDto>()
                .content(entityPage.stream()
                        .map(materialEntityMapper::toDto).toList())
                .totalElements(entityPage.getTotalElements());
    }
}
