package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.NameTechnologyRepeatedException;
import com.pragma.bootcamp.domain.exception.NumberTechnolgiesLessThanTreeException;
import com.pragma.bootcamp.domain.exception.NumberTechnologiesGreaterThanTwentyException;

import java.util.HashSet;
import java.util.List;

public class Capacity {

    private final Long id;
    private static final  Integer DEFAULT_MIN_NUMBER_TECHNOLOGIES = 3;
    private static final Integer DEFAULT_MAX_NUMBER_TECHNOLOGIES = 20;
    private final String name;
    private final String description;
    private final List<Technology> technologyList;

    public Capacity(Long id, String name, String description, List<Technology> technologyList) {

        executeValidationTechnologyRanges(technologyList);
        executeValidationNameTechnologyRepeated(technologyList);

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

    private void executeValidationTechnologyRanges(List<Technology> technologyList) {

        if (technologyList.size() < DEFAULT_MIN_NUMBER_TECHNOLOGIES) {
            throw new NumberTechnolgiesLessThanTreeException("el minimo de tecnolgias que se pueden agregar a la capacidad es de " + DEFAULT_MIN_NUMBER_TECHNOLOGIES + " tecnologias");
        }
        if (technologyList.size() > DEFAULT_MAX_NUMBER_TECHNOLOGIES) {
            throw new NumberTechnologiesGreaterThanTwentyException("el maximo e tecnolgias que se pueden agregar a la capacidad es de " + DEFAULT_MAX_NUMBER_TECHNOLOGIES+ " tecnologias");
        }
    }

    private void executeValidationNameTechnologyRepeated(List<Technology> technologyList) {

        HashSet<String> uniqueName = new HashSet<>(technologyList.size());

        technologyList.forEach(technology -> {
            if (!uniqueName.add(technology.getName()))
                throw new NameTechnologyRepeatedException("La capacidad no debe tener tecnologias repetidas");
        });

    }
}
