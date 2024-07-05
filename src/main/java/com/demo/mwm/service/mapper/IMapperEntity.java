package com.demo.mwm.service.mapper;

import org.mapstruct.MappingTarget;


public interface IMapperEntity<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    void updateFromDto(@MappingTarget E entity, D dto);

}
