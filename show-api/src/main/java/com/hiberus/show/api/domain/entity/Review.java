package com.hiberus.show.api.domain.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Review {

    int rating;
    String comment;
}
