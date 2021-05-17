package com.hiberus.show.api.service;

import com.hiberus.show.api.mapper.ShowMapper;
import com.hiberus.show.api.repository.ShowApiRepository;
import com.hiberus.show.library.dto.ShowDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowApiServiceImpl implements ShowApiService {

    private final ShowApiRepository showApiRepository;
    private final ShowMapper showMapper = Mappers.getMapper(ShowMapper.class);

    @Override
    public ShowDto[] retrieveAllShows() {
        return showApiRepository.findAll().stream().map(showMapper::mapShow).toArray(ShowDto[]::new);
    }

    @Override
    public Optional<ShowDto> retrieveShowByIdentifier(final String identifier) {
        return showApiRepository.findById(identifier).map(showMapper::mapShow);
    }
}
