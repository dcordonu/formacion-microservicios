package com.hiberus.show.api.service;

import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.mapper.ShowMapper;
import com.hiberus.show.api.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowApiServiceImpl implements ShowApiService {

    private final ShowRepository showRepository;
    private final ShowMapper showMapper = Mappers.getMapper(ShowMapper.class);

    @Override
    public ShowDto[] retrieveAllShows() {
        return showRepository.findAll().stream().map(showMapper::mapShow).toArray(ShowDto[]::new);
    }

    @Override
    public Optional<ShowDto> retrieveShowByIdentifier(final String identifier) {
        return showRepository.findById(identifier).map(showMapper::mapShow).or(Optional::empty);
    }
}
