package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ICapacityResponseMapper.class})
public interface IBootcampResponseMapper {

	@Mapping(source = "capacityList", target = "capacities")
	BootcampResponse modelToResponse(Bootcamp bootcamp);

	@Mapping(source = "capacityList", target = "capacities")
	List<BootcampResponse> toResponseList(List<Bootcamp> bootcampList);

	default PaginationResponse<BootcampResponse> toPaginationResponse(PaginationResponse<Bootcamp> paginationBootcamp) {

		if ( paginationBootcamp == null ) {
			return null;
		}

		return new PaginationResponse.Builder<BootcampResponse>()
				.content(toResponseList(paginationBootcamp.getContent()))
				.isEmpty(paginationBootcamp.isEmpty())
				.isFirst(paginationBootcamp.isFirst())
				.isLast(paginationBootcamp.isLast())
				.pageNumber(paginationBootcamp.getPageNumber())
				.pageSize(paginationBootcamp.getPageSize())
				.totalElements(paginationBootcamp.getTotalElements())
				.totalPages(paginationBootcamp.getTotalPages())
				.build();
	}
}
