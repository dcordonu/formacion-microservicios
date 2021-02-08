package com.hiberus.show.api.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "shows")
public class Show {

    @Id
    private final String identifier;
    private final String name;
    private final String[] availablePlatforms;
    private final Review[] reviews;

    public Show(final String identifier, final String name, final String[] availablePlatforms, final Review[] reviews) {
        this.identifier = identifier;
        this.name = name;
        this.availablePlatforms = availablePlatforms;
        this.reviews = reviews;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String[] getAvailablePlatforms() {
        return availablePlatforms;
    }

    public Review[] getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Show{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", availablePlatforms=" + Arrays.toString(availablePlatforms) +
                ", reviews=" + Arrays.toString(reviews) +
                '}';
    }
}
