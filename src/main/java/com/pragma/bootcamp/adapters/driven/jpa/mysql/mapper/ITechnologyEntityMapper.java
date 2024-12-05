package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {

  @Mapping(target = "capacityEntities", ignore = true)//→ evitará los warning de los test al no mappear todos los elementos
  TechnologyEntity modelToEntity(Technology technology);
  Technology entityToModel(TechnologyEntity technologyEntity);
  List<TechnologyEntity> toEntityList(List<Technology> technologies);
  List<Technology> toModelList(List<TechnologyEntity> technologyEntities);
}
