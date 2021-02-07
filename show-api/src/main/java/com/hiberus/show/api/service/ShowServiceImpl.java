package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.entity.Show;
import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public ShowServiceImpl(final ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public ShowDto[] retrieveAllShows() {
        final List<ShowDto> showDtoList = new ArrayList<>();
        final List<Show> shows = showRepository.findAll();

        shows.forEach(show -> showDtoList.add(new ShowDto(show.getIdentifier(), show.getName(), show.getAvailablePlatforms(), show.getRatings())));

        return showDtoList.toArray(new ShowDto[0]);
    }

    @Override
    public Optional<ShowDto> retrieveShowByIdentifier(final String identifier) {
        final Optional<Show> show = showRepository.findById(identifier);

        return show.map(s -> new ShowDto(s.getIdentifier(), s.getName(), s.getAvailablePlatforms(), s.getRatings())).or(Optional::empty);
    }
}
