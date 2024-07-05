package com.vsii.mwm.service.mapper;

import com.vsii.mwm.domain.SupplierEntity;
import com.vsii.mwm.service.dto.SupplierDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;


@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface ISupplierEntityMapper extends IMapperEntity<SupplierDto, SupplierEntity> {

    @Mapping(target = "id", ignore = true)
    @Override
    SupplierEntity toEntity(SupplierDto dto);

    @Override
    SupplierDto toDto(SupplierEntity entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void updateFromDto(@MappingTarget SupplierEntity entity, SupplierDto dto);


}
