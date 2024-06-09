package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IBootcampRepository extends JpaRepository<BootcampEntity, Long>, JpaSpecificationExecutor<BootcampEntity> {

	Optional<BootcampEntity> findByNameIgnoreCase(String name);

	@Query("SELECT b FROM BootcampEntity b ORDER BY SIZE(b.capacityEntities) ASC")
	Page<BootcampEntity> findAllOrderedByBootcampSizeAsc(Pageable pageable);

	@Query("SELECT b FROM BootcampEntity b ORDER BY SIZE(b.capacityEntities) DESC")
	Page<BootcampEntity> findAllOrderedByBootcampSizeDesc(Pageable pageable);
}
