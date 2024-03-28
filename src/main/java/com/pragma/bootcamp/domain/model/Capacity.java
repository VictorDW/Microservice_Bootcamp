package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
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

        executeValidationTechnologyRange(technologyList);
        executeValidationNameTechnologyRepeated(technologyList);

        this.id = id;
        this.name = name;
        this.description = description;
        this.technologyList = technologyList;
    }

    private void executeValidationTechnologyRange(List<Technology> technologyList) {

        int technologySize = technologyList.size();

        if (technologySize < DEFAULT_MIN_NUMBER_TECHNOLOGIES || technologySize > DEFAULT_MAX_NUMBER_TECHNOLOGIES) {

            throw new NumberOutOfRangeException(
                String.format(
                    DomainConstants.NUMBER_RANGE_MESSAGE,
                    DomainConstants.Class.TECHNOLOGY.getPluralName(),
                    DomainConstants.Class.CAPACITY.getName(),
                    Capacity.DEFAULT_MIN_NUMBER_TECHNOLOGIES,
                    Capacity.DEFAULT_MAX_NUMBER_TECHNOLOGIES));
        }
    }

    private void executeValidationNameTechnologyRepeated(List<Technology> technologyList) {

        HashSet<String> uniqueName = new HashSet<>(technologyList.size());

        technologyList.forEach(technology -> {
            if (!uniqueName.add(technology.getName()))
                throw new RepeatedModelException(
                    String.format(
                        DomainConstants.REPEATED_MODEL_MESSAGE,
                        DomainConstants.Class.CAPACITY.getName(),
                        DomainConstants.Class.TECHNOLOGY.getPluralName()));
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
