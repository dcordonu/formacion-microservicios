package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.dto.ReviewDto;
import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.domain.entity.Show;
import com.hiberus.show.api.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowApiServiceImpl implements ShowApiService {

    private final ShowRepository showRepository;

    @Override
    public ShowDto[] retrieveAllShows() {
        final List<Show> shows = showRepository.findAll();

        return shows.stream().map(s -> ShowDto.builder()
                .identifier(s.getIdentifier())
                .title(s.getName())
                .reviews(s.getReviews() != null ? Arrays.stream(s.getReviews()).map(r -> ReviewDto.builder()
                        .rating(r.getRating())
                        .comment(r.getComment())
                        .build()).toArray(ReviewDto[]::new) : new ReviewDto[0])
                .availablePlatforms(s.getAvailablePlatforms())
                .build()).toArray(ShowDto[]::new);
    }

    @Override
    public Optional<ShowDto> retrieveShowByIdentifier(final String identifier) {
        final Optional<Show> show = showRepository.findById(identifier);

        return show.map(s -> ShowDto.builder()
                .title(s.getName())
                .availablePlatforms(s.getAvailablePlatforms())
                .reviews(s.getReviews() != null ? Arrays.stream(s.getReviews()).map(r -> ReviewDto.builder()
                        .rating(r.getRating())
                        .comment(r.getComment())
                .build()).toArray(ReviewDto[]::new) : new ReviewDto[0])
                .identifier(s.getIdentifier())
        .build()).or(Optional::empty);
    }
}
