package com.demo.mwm.service.mapper;

import com.demo.mwm.dto.TransactionDto;
import com.demo.mwm.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITransactionEntityMapper extends IMapperEntity<TransactionDto, TransactionEntity> {

    @Override
    TransactionEntity toEntity(TransactionDto dto);

    @Override
    TransactionDto toDto(TransactionEntity entity);


}
