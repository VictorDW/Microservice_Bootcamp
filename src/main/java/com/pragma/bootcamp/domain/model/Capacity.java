package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import com.pragma.bootcamp.domain.util.order.IOrderBy;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Capacity extends ParentModel {

    public static final  Integer DEFAULT_MIN_NUMBER_TECHNOLOGIES = 3;
    public static final Integer DEFAULT_MAX_NUMBER_TECHNOLOGIES = 20;

    private List<Technology> technologyList;

    public Capacity(Long id, String name, String description) {
      super(id, name, description);
      this.technologyList = new ArrayList<>();
    }

    public void setTechnologyList(List<Technology> technologyList) {

    ModelValidationUtil.executeValidationModel(
        technologyList,
        DomainConstants.Class.TECHNOLOGY.getPluralName(),
        DomainConstants.Class.CAPACITY.getName(),
        DEFAULT_MIN_NUMBER_TECHNOLOGIES,
        DEFAULT_MAX_NUMBER_TECHNOLOGIES);

    this.technologyList = technologyList;
    }

    public List<Technology> getTechnologyList() {
        return technologyList;
    }

    public enum OrderBy implements IOrderBy {
        NAME("name"),
        TECHNOLOGIES("technologies");

        private final String order;
        OrderBy(String order) {
            this.order = order;
        }
        @Override
        public String getOrderBy() {
            return order;
        }
    }
}
