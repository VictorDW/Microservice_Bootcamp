package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.VersionEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IVersionRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.IVersionPersistencePort;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class VersionPersistenceAdapter implements IVersionPersistencePort, IPaginationProvider<Version, VersionEntity> {

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
        .orElseThrow(()-> bootcampThrow(bootcampName, Constants.Field.NOMBRE.name().toLowerCase()));
  }

  private <T> RuntimeException bootcampThrow(T bootcamp, String field)  {
	  throw new NoEntityFoundException(
			  messagePort.getMessage(
					  Constants.NOT_FOUND_BOOTCAMP_MESSAGE,
            field,
            bootcamp));
  }

  @Override
  public List<Version> getAllVersion(Long bootcampId, PaginationData paginationData) {

    final Pageable pagination = paginationWithSorting(paginationData);
    Page<VersionEntity> versionEntities;

    if (Objects.isNull(bootcampId)) {
      versionEntities = versionRepository.findAll(pagination);
    } else {
      BootcampEntity bootcampEntity = getBootcampEntity(bootcampId);
      versionEntities = versionRepository.findAllByBootcampEntity(bootcampEntity, pagination);
    }

    return versionEntities.map(versionEntityMapper::entityToModel).toList();
  }

  private BootcampEntity getBootcampEntity(Long bootcampId) {
    return bootcampRepository
        .findById(bootcampId)
        .orElseThrow(() -> bootcampThrow(bootcampId, Constants.Field.ID.name().toLowerCase()));
  }

}
