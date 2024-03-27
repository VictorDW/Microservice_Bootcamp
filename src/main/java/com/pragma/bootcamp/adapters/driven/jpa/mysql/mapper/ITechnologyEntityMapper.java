package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {

  TechnologyEntity modelToEntity(Technology technology);
  Technology entityToModel(TechnologyEntity technologyEntity);
  List<Technology> toModelList(List<TechnologyEntity> technologyEntities);
}
