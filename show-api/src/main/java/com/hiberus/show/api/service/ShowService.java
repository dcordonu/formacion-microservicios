package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.dto.ShowDto;

import java.util.Optional;

public interface ShowService {

    ShowDto[] retrieveAllShows();

    Optional<ShowDto> retrieveShowByIdentifier(final String identifier);
}
