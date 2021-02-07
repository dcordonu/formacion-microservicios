package com.hiberus.show.api.controller;

import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/show", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShowControllerImpl implements ShowController {

    private final ShowService showService;

    @Autowired
    public ShowControllerImpl(final ShowService showService) {
        this.showService = showService;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<ShowDto[]> retrieveAllShows() {
        final ShowDto[] shows = showService.retrieveAllShows();

        return ResponseEntity.ok(shows);
    }

    @Override
    @GetMapping(value = "/{identifier}")
    public ResponseEntity<ShowDto> retrieveShowById(@PathVariable("identifier") final String identifier) {
        return showService.retrieveShowByIdentifier(identifier).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
