package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.VersionEntity;
import com.pragma.bootcamp.domain.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IVersionEntityMapper {

  @Mapping(target = "bootcampEntity", ignore = true)
  VersionEntity modelToEntity(Version version);

  @Mapping(target = "bootcampName", source = "bootcampEntity.name")
  Version entityToModel(VersionEntity versionEntity);

}
