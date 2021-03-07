package com.hiberus.show.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonPropertyOrder(alphabetic = true)
public class ShowDto {

    String identifier;
    String title;
    String[] availablePlatforms;
    ReviewDto[] reviews;

    ShowDto(String identifier, String title, String[] availablePlatforms, ReviewDto[] reviews) {
        this.identifier = identifier;
        this.title = title;
        this.availablePlatforms = availablePlatforms != null ? availablePlatforms : new String[0];
        this.reviews = reviews != null ? reviews : new ReviewDto[0];
    }
}
