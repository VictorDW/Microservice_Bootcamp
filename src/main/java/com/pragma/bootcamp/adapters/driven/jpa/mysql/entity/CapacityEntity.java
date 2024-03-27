package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "capacity")
public class CapacityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 90)
    private String description;

    public static final String FIELD_CONTAINING_RELATIONSHIP = "technologyEntities";

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
       name = "capacity_technology_mapping",
       joinColumns = @JoinColumn(name = "capacity_id", referencedColumnName = "id"),
       inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
    )
    List<TechnologyEntity> technologyEntities = new ArrayList<>();
}
