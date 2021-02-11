package com.hiberus.show.api.controller;

import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.service.ShowApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/show", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShowApiApiControllerImpl implements ShowApiController {

    private final ShowApiService showApiService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<ShowDto[]> retrieveAllShows() {
        final ShowDto[] shows = showApiService.retrieveAllShows();

        return ResponseEntity.ok(shows);
    }

    @Override
    @GetMapping(value = "/{identifier}")
    public ResponseEntity<ShowDto> retrieveShowById(@PathVariable("identifier") final String identifier) {
        return showApiService.retrieveShowByIdentifier(identifier).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
