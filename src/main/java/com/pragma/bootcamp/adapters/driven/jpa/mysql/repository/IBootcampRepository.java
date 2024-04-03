package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IBootcampRepository extends JpaRepository<BootcampEntity, Long>, JpaSpecificationExecutor<BootcampEntity> {

	Optional<BootcampEntity> findByNameIgnoreCase(String name);
}
