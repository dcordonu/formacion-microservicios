package com.hiberus.show.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@JsonPropertyOrder(alphabetic = true)
public class ReviewDto {

    int rating;
    String comment;
}
