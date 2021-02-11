package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.domain.entity.Show;
import com.hiberus.show.api.repository.ShowRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowApiServiceTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowApiServiceImpl showService;

    @Test
    public void testRetrieveAllShows() {
        when(showRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(showService.retrieveAllShows()).isEmpty();

        when(showRepository.findAll()).thenReturn(Collections.singletonList(Show.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .name("Tenet")
                .build()));
        assertThat(showService.retrieveAllShows().length).isEqualTo(1);
    }

    @Test
    public void testRetrieveShowById() {
        when(showRepository.findById("1")).thenReturn(Optional.of(Show.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .name("Tenet")
                .reviews(null)
                .build()));
        when(showRepository.findById("2")).thenReturn(Optional.empty());

        assertThat(showService.retrieveShowByIdentifier("1")).isPresent();
        assertThat(showService.retrieveShowByIdentifier("1")).get().isEqualTo(ShowDto.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .title("Tenet")
                .build());
        assertThat(showService.retrieveShowByIdentifier("2")).isEmpty();
    }
}
