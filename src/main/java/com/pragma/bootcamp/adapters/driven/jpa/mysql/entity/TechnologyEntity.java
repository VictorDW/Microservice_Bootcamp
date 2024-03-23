package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "technology")
public class TechnologyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 90)
  private String description;

  @ManyToMany(mappedBy = "technologyEntities")
  private Set<CapacityEntity> capacityEntities = new HashSet<>();
}
