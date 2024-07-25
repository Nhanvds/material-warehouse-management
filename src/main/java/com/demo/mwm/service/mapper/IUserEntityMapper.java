package com.demo.mwm.service.mapper;

import com.demo.mwm.dto.UserDto;
import com.demo.mwm.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IUserEntityMapper extends IMapperEntity<UserDto, UserEntity> {
    /**
     *
     * @param entity
     * @return Dto
     */
    @Mapping(target = "password",ignore = true)
    @Override
    UserDto toDto(UserEntity entity);
}
