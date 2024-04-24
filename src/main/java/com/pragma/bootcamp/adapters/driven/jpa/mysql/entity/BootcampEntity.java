package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "bootcamp")
public class BootcampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 90)
	private String description;

	public static final String FIELD_CONTAINING_RELATIONSHIP = "capacityEntities";

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "bootcamp_capacity_mapping",
			joinColumns = @JoinColumn(name = "bootcamp_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "capacity_id", referencedColumnName = "id")
	)
	private List<CapacityEntity> capacityEntities = new ArrayList<>();
}
