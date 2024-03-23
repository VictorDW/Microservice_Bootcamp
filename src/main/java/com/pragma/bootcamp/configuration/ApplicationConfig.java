package com.pragma.bootcamp.configuration;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.CapacityPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.TechnologyPersistenceAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.adapters.driven.message.adapter.MessageAdapter;
import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.api.usecase.CapacityUseCase;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.api.usecase.TechnologyUseCase;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ICapacityEntityMapper capacityEntityMapper;
  private final ITechnologyRepository technologyRepository;
  private final ICapacityRepository capacityRepository;
  private final MessageSource messageSource;

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
    return new CapacityPersistenceAdapter(capacityEntityMapper, technologyRepository, capacityRepository);
  }

  public ICapacityServicePort capacityServicePort() {
    return new CapacityUseCase(capacityPersistencePort(), messagePort());
  }
}
