package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;


import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {
    CapacityEntity capacityToCapacityEntity(Capacity capacity);
    Capacity capacityEntityToCapacity(CapacityEntity capacityEntity);

}
