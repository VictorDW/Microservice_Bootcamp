package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface ICapacityRepository extends JpaRepository<CapacityEntity, Long>, JpaSpecificationExecutor<CapacityEntity> {

    Optional<CapacityEntity> findByNameIgnoreCase(String name);

    @Query("SELECT c FROM CapacityEntity c ORDER BY SIZE(c.technologyEntities) ASC")
    Page<CapacityEntity> findAllOrderedByTechnologySizeAsc(Pageable pageable);

    @Query("SELECT c FROM CapacityEntity c ORDER BY SIZE(c.technologyEntities) DESC")
    Page<CapacityEntity> findAllOrderedByTechnologySizeDesc(Pageable pageable);
}
