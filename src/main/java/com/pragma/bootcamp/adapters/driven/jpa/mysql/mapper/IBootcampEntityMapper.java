package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampEntityMapper {

	@Mapping(target = "capacityEntities", ignore = true) //→ evitará los warning de los test al no mappear todos los elementos
	BootcampEntity modelToEntity(Bootcamp bootcamp);

	@Mapping(source = "capacityEntities", target = "capacityList")
	Bootcamp entityToModel(BootcampEntity bootcampEntity);

	@Mapping(source = "technologyEntities", target = "technologyList")
	Capacity entityToModel(CapacityEntity capacityEntity);

	@Mapping(source = "capacityEntities", target = "capacityList")
	List<Bootcamp> toModelList(List<BootcampEntity> bootcampEntityList);
}
