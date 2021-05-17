package com.hiberus.show.library.repository;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Review {

    int rating;
    String comment;
}
