package com.hiberus.show.library.repository;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(collection = "shows")
public class Show {

    @Id
    String identifier;
    String name;
    String[] availablePlatforms;
    Review[] reviews;
    String isan;

    Show(String identifier, String name, String[] availablePlatforms, Review[] reviews, String isan) {
        this.identifier = identifier;
        this.name = name;
        this.availablePlatforms = availablePlatforms != null ? availablePlatforms : new String[0];
        this.reviews = reviews != null ? reviews : new Review[0];
        this.isan = isan;
    }
}
