package com.pragma.bootcamp.adapters.driven.jpa.mysql.util;

import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IPaginationProvider<T, K> {

  default Pageable paginationWithSorting(PaginationData paginationData) {

    Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
    return PageRequest.of(paginationData.page(), paginationData.size(), sort);
  }
  
  default Pageable simplePagination(PaginationData paginationData) {
    return PageRequest.of(paginationData.page(), paginationData.size());
  }

  default PaginationResponse<T> builderPaginationResponse(List<T> modelList, Page<K> page) {
    return new PaginationResponse.Builder<T>()
        .content(modelList)
        .isEmpty(page.isEmpty())
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .build();
  }


}
