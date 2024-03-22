package com.pragma.bootcamp.domain.model;

import java.util.List;

public class Capacity {

    private final Long id;
    private final String name;
    private final String description;
    private final List<Technology> technologyList;

    public Capacity(Long id, String name, String description, List<Technology> technologyList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.technologyList = technologyList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Technology> getTechnologyList() {
        return technologyList;
    }
}
