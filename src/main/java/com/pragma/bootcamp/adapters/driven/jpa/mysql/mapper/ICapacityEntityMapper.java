package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;


import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {


    @Mapping(target = "technologyEntities", ignore = true)//→ evitará los warning de los test al no mappear todos los elementos
    @Mapping(target = "bootcampEntities", ignore = true)//→ evitará los warning de los test al no mappear todos los elementos
    CapacityEntity modelToEntity(Capacity capacity);

    /* → en este caso se especificó como se llama el campo en la entity(source) y como se llama en el modelo(target),
     para asi se MapStruct lo mapee */
    @Mapping(source = "technologyEntities", target = "technologyList")
    Capacity entityToModel(CapacityEntity capacityEntity);
    @Mapping(source = "technologyEntities", target = "technologyList")
    List<Capacity> toModelList(List<CapacityEntity> capacityEntities);
}
