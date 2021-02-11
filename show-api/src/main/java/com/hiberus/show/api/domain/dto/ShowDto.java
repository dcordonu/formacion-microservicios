package com.hiberus.show.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@JsonPropertyOrder(alphabetic = true)
public class ShowDto {

    @NonNull
    private final String identifier;

    @NonNull
    private final String title;

    @Builder.Default
    private final String[] availablePlatforms = new String[0];

    @Builder.Default
    private final ReviewDto[] reviews = new ReviewDto[0];
}
