package com.pragma.bootcamp.configuration;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.BootcampPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.CapacityPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.TechnologyPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.VersionPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IVersionRepository;
import com.pragma.bootcamp.adapters.driven.message.adapter.MessageAdapter;
import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.api.IVersionServicePort;
import com.pragma.bootcamp.domain.api.usecase.BootcampUseCase;
import com.pragma.bootcamp.domain.api.usecase.CapacityUseCase;
import com.pragma.bootcamp.domain.api.usecase.VersionUseCase;
import com.pragma.bootcamp.domain.spi.*;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.api.usecase.TechnologyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final MessageSource messageSource;
  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ITechnologyRepository technologyRepository;

  private final ICapacityEntityMapper capacityEntityMapper;
  private final ICapacityRepository capacityRepository;

  private final IBootcampEntityMapper bootcampEntityMapper;
  private final IBootcampRepository bootcampRepository;

  private final IVersionRepository versionRepository;
  private final IVersionEntityMapper versionEntityMapper;


  @Bean
  public IMessagePort messagePort() {
    return new MessageAdapter(messageSource);
  }

  @Bean
  public ITechnologyPersistencePort technologyPersistencePort() {
    return new TechnologyPersistenceAdapter(technologyEntityMapper, technologyRepository);
  }

  @Bean
  public ITechnologyServicePort technologyServicePort() {
    return new TechnologyUseCase(technologyPersistencePort(), messagePort());
  }
  @Bean
  public ICapacityPersistencePort capacityPersistencePort() {
    return new CapacityPersistenceAdapter(capacityEntityMapper, technologyRepository, capacityRepository, messagePort());
  }

  @Bean
  public ICapacityServicePort capacityServicePort() {
    return new CapacityUseCase(capacityPersistencePort(), messagePort());
  }

  @Bean
  public IBootcampPersistencePort bootcampPersistencePort() {
    return new BootcampPersistenceAdapter(bootcampEntityMapper, capacityRepository, bootcampRepository, messagePort());
  }

  @Bean
  public IBootcampServicePort bootcampServicePort() {
    return new BootcampUseCase(bootcampPersistencePort(), messagePort());
  }

  @Bean
  public IVersionPersistencePort versionPersistencePort() {
    return new VersionPersistenceAdapter(versionEntityMapper, bootcampRepository, versionRepository, messagePort());
  }

  @Bean
  public IVersionServicePort versionServicePort() {
    return new VersionUseCase(versionPersistencePort(), messagePort());
  }
}
