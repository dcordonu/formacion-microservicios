package com.hiberus.show.api.service;


import com.hiberus.show.library.dto.ShowDto;

import java.util.Optional;

public interface ShowApiService {

    ShowDto[] retrieveAllShows();

    Optional<ShowDto> retrieveShowByIdentifier(final String identifier);
}
