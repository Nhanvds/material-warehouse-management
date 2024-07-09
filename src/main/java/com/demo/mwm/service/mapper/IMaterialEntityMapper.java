package com.demo.mwm.service.mapper;

import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.dto.MaterialDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IMaterialEntityMapper extends IMapperEntity<MaterialDto, MaterialEntity> {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    @Override
    MaterialEntity toEntity(MaterialDto dto);

    @Mapping(target = "supplierId", ignore = true)
    @Override
    MaterialDto toDto(MaterialEntity entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void updateFromDto(@MappingTarget MaterialEntity entity, MaterialDto dto);
}
