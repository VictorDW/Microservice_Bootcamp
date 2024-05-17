package com.pragma.bootcamp.domain.util.pagination;

import java.util.List;

public class PaginationResponse<T> {

  private final List<T> content;
  private final boolean isEmpty;
  private final boolean isFirst;
  private final boolean isLast;
  private final Integer pageNumber;
  private final Integer pageSize;
  private final Long totalElements;
  private final Integer totalPages;


  public PaginationResponse(Builder<T> builder) {
    this.content = builder.content;
    this.isEmpty = builder.isEmpty;
    this.isFirst = builder.isFirst;
    this.isLast = builder.isLast;
    this.pageNumber = builder.pageNumber;
    this.pageSize = builder.pageSize;
    this.totalElements = builder.totalElements;
    this.totalPages = builder.totalPages;
  }

  public List<T> getContent() {
    return content;
  }

  public boolean isEmpty() {
    return isEmpty;
  }

  public boolean isFirst() {
    return isFirst;
  }

  public boolean isLast() {
    return isLast;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public static class Builder<T> {
    private List<T> content;
    private boolean isEmpty;
    private boolean isFirst;
    private boolean isLast;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    public Builder<T> content(List<T> content) {
      this.content = content;
      return this;
    }

    public Builder<T> isEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
      return this;
    }

    public Builder<T> isFirst(boolean isFirst) {
      this.isFirst = isFirst;
      return this;
    }

    public Builder<T> isLast(boolean isLast) {
      this.isLast = isLast;
      return this;
    }

    public Builder<T> pageNumber(Integer pageNumber) {
      this.pageNumber = pageNumber;
      return this;
    }

    public Builder<T> pageSize(Integer pageSize) {
      this.pageSize = pageSize;
      return this;
    }

    public Builder<T> totalElements(Long totalElements) {
      this.totalElements = totalElements;
      return this;
    }

    public Builder<T> totalPages(Integer totalPages) {
      this.totalPages = totalPages;
      return this;
    }

    public  PaginationResponse<T> build() {
      return new PaginationResponse<>(this);
    }
  }
}
