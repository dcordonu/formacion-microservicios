package com.hiberus.show.api.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "shows")
public class Show {

    @Id
    private final String identifier;
    private final String name;

    @Builder.Default
    private final String[] availablePlatforms = new String[0];

    @Builder.Default
    private final Review[] reviews = new Review[0];
}
