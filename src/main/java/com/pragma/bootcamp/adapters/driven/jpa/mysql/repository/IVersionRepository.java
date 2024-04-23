package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;


import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.VersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVersionRepository extends JpaRepository<VersionEntity, Long> {

	Page<VersionEntity> findAllByBootcampEntity(BootcampEntity bootcampEntity, Pageable pageable);
}
