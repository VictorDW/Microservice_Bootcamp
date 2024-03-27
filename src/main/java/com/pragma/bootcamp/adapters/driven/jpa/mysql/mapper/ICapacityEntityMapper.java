package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;


import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {

    @Mapping(target = "technologyEntities", ignore = true)
    CapacityEntity modelToEntity(Capacity capacity);

    /* -> en este caso se especifico como se llama el campo en la entity(source) y como se llama en el modelo(target),
     para asi se MapStruct lo mapee */
    @Mapping(source = "technologyEntities", target = "technologyList")
    Capacity entityToModel(CapacityEntity capacityEntity);
    @Mapping(source = "technologyEntities", target = "technologyList")
    List<Capacity> ToModelList(List<CapacityEntity> capacityEntities);
}
