package com.hiberus.show.api.service;

import com.hiberus.show.api.repository.ShowApiRepository;
import com.hiberus.show.library.dto.ShowDto;
import com.hiberus.show.library.repository.Show;
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
    private ShowApiRepository showApiRepository;

    @InjectMocks
    private ShowApiServiceImpl showApiService;

    @Test
    public void testRetrieveAllShows() {
        when(showApiRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(showApiService.retrieveAllShows()).isEmpty();

        when(showApiRepository.findAll()).thenReturn(Collections.singletonList(Show.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .name("Tenet")
                .build()));
        assertThat(showApiService.retrieveAllShows().length).isEqualTo(1);
    }

    @Test
    public void testRetrieveShowById() {
        when(showApiRepository.findById("1")).thenReturn(Optional.of(Show.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .name("Tenet")
                .reviews(null)
                .build()));
        when(showApiRepository.findById("2")).thenReturn(Optional.empty());

        assertThat(showApiService.retrieveShowByIdentifier("1")).isPresent();
        assertThat(showApiService.retrieveShowByIdentifier("1")).get().isEqualTo(ShowDto.builder()
                .availablePlatforms(Collections.singletonList("HBO").toArray(new String[0]))
                .identifier("1")
                .title("Tenet")
                .build());
        assertThat(showApiService.retrieveShowByIdentifier("2")).isEmpty();
    }
}
