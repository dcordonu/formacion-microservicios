package com.hiberus.show.api.mapper;

import com.hiberus.show.library.dto.ShowDto;
import com.hiberus.show.library.repository.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ShowMapper {

    @Mapping(target = "title", source = "name")
    ShowDto mapShow(final Show show);
}
