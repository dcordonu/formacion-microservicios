package com.hiberus.show.api.mapper;

import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.domain.entity.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ShowMapper {

    @Mapping(target = "title", source = "name")
    ShowDto mapShow(final Show show);
}
