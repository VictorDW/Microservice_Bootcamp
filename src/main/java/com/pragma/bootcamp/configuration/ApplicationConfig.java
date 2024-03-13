package com.pragma.bootcamp.configuration;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.TechnologyPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.api.usecase.TechnologyUseCase;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ITechnologyRepository technologyRepository;

  @Bean
  public ITechnologyPersistencePort technologyPersistencePort() {
    return new TechnologyPersistenceAdapter(technologyEntityMapper, technologyRepository);
  }

  @Bean
  public ITechnologyServicePort technologyServicePort() {
    return new TechnologyUseCase(technologyPersistencePort());
  }
}
