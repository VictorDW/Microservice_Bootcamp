package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.domain.util.PaginationData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface ISimplePagination {

  default Pageable simplePagination(PaginationData paginationData) {

    Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
    return PageRequest.of(paginationData.page(), paginationData.size(), sort);
  }


}
