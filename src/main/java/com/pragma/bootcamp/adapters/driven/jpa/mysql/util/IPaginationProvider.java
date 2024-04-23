package com.pragma.bootcamp.adapters.driven.jpa.mysql.util;

import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface IPaginationProvider {

  default Pageable paginationWithSorting(PaginationData paginationData) {

    Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
    return PageRequest.of(paginationData.page(), paginationData.size(), sort);
  }
  
  default Pageable simplePagination(PaginationData paginationData) {
    return PageRequest.of(paginationData.page(), paginationData.size());
  }


}
