package com.vsii.mwm.service.mapper;

import com.vsii.mwm.domain.MaterialEntity;
import com.vsii.mwm.service.dto.MaterialDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;


@Component
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
