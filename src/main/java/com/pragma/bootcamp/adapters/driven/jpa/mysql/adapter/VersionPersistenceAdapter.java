package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.VersionEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IVersionRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.IVersionPersistencePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VersionPersistenceAdapter implements IVersionPersistencePort {

  private final IVersionEntityMapper versionEntityMapper;
  private final IBootcampRepository bootcampRepository;
  private final IVersionRepository versionRepository;
  private final IMessagePort messagePort;

  @Override
  public Version saveVersion(Version version) {

    VersionEntity versionEntity = versionEntityMapper.modelToEntity(version);
    versionEntity.setBootcampEntity(getBootcampEntity(version.getBootcampName()));
    VersionEntity savedEntity = versionRepository.save(versionEntity);

    return versionEntityMapper.entityToModel(savedEntity);
  }

  private BootcampEntity getBootcampEntity(String bootcampName) {

    return bootcampRepository
        .findByNameIgnoreCase(bootcampName)
        .orElseThrow(()->
            new NoEntityFoundException(
                messagePort.getMessage(
                    Constants.NOT_FOUND_BOOTCAMP_MESSAGE,
                    bootcampName))
        );
  }
}
