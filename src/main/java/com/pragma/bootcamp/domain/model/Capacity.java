package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.RepeatedTechnologyException;
import com.pragma.bootcamp.domain.exception.NumberTechnolgiesLessThanException;
import com.pragma.bootcamp.domain.exception.NumberTechnologiesGreaterThanException;
import com.pragma.bootcamp.domain.util.DomainConstants;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;

@EqualsAndHashCode
public class Capacity {

    private final Long id;
    public static final  Integer DEFAULT_MIN_NUMBER_TECHNOLOGIES = 3;
    public static final Integer DEFAULT_MAX_NUMBER_TECHNOLOGIES = 20;
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

    private void executeValidationTechnologyRanges(List<Technology> technologyList) {

        if (technologyList.size() < DEFAULT_MIN_NUMBER_TECHNOLOGIES) {
            throw new NumberTechnolgiesLessThanException(
                String.format(DomainConstants.NUMBER_TECHNOLOGIES_MIN_MESSAGE, Capacity.DEFAULT_MIN_NUMBER_TECHNOLOGIES)
            );
        }
        if (technologyList.size() > DEFAULT_MAX_NUMBER_TECHNOLOGIES) {
            throw new NumberTechnologiesGreaterThanException(
                String.format(DomainConstants.NUMBER_TECHNOLOGIES_MAX_MESSAGE, Capacity.DEFAULT_MAX_NUMBER_TECHNOLOGIES)
            );
        }
    }

    private void executeValidationNameTechnologyRepeated(List<Technology> technologyList) {

        HashSet<String> uniqueName = new HashSet<>(technologyList.size());

        technologyList.forEach(technology -> {
            if (!uniqueName.add(technology.getName()))
                throw new RepeatedTechnologyException(DomainConstants.REPEATED_TECHNOLOGY_MESSAGE);
        });

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
