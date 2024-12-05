package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "version")
public class VersionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bootcamp_id", nullable = false)
  private BootcampEntity bootcampEntity;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "maximum_capacity", nullable = false)
  private Integer maximumCapacity;
}
