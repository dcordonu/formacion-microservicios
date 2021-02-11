package com.hiberus.show.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder(alphabetic = true)
public class ReviewDto {

    private final int rating;
    private final String comment;
}
