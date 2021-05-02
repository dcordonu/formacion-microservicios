package com.hiberus.show.api.controller;

import com.hiberus.show.api.domain.dto.ShowDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ShowApiController {

    @ApiOperation(value = "Returns all shows stored in the database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Data found", response = ShowDto[].class)
    })
    ResponseEntity<ShowDto[]> retrieveAllShows();

    @ApiOperation(value = "Searchs for a show with the given id in the database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Show found", response = ShowDto.class),
            @ApiResponse(code = 404, message = "Show not found")
    })
    ResponseEntity<ShowDto> retrieveShowById(final String identifier);
}
