package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.dto.RatingDto;
import com.hiberus.show.api.domain.entity.Rating;
import com.hiberus.show.api.domain.entity.Show;
import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

        shows.forEach(show -> {
            final RatingDto[] ratingDtos;

            if (show.getRatings() != null) {
                final List<RatingDto> ratingDtoList = new ArrayList<>();
                Arrays.stream(show.getRatings()).forEach(rating -> ratingDtoList.add(new RatingDto(rating.getPunctuation(), rating.getComment())));
                ratingDtos = ratingDtoList.toArray(new RatingDto[0]);
            } else {
                ratingDtos = null;
            }

            showDtoList.add(new ShowDto(show.getIdentifier(), show.getName(), show.getAvailablePlatforms(), ratingDtos));
        });

        return showDtoList.toArray(new ShowDto[0]);
    }

    @Override
    public Optional<ShowDto> retrieveShowByIdentifier(final String identifier) {
        final Optional<Show> show = showRepository.findById(identifier);

        return show.map(s -> {
            final List<RatingDto> ratingDtos = new ArrayList<>();
            Arrays.stream(s.getRatings()).forEach(rating -> ratingDtos.add(new RatingDto(rating.getPunctuation(), rating.getComment())));
            return new ShowDto(s.getIdentifier(), s.getName(), s.getAvailablePlatforms(), ratingDtos.toArray(new RatingDto[0]));
        }).or(Optional::empty);
    }
}
