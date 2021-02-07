package com.hiberus.show.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiberus.show.api.domain.entity.Rating;

public class ShowDto {

    private final String identifier;
    private final String name;

    @JsonProperty(value = "available_platforms")
    private final String[] availablePlatforms;

    private final Rating[] ratings;

    public ShowDto(final String identifier, final String name, final String[] availablePlatforms, final Rating[] ratings) {
        this.identifier = identifier;
        this.name = name;
        this.availablePlatforms = availablePlatforms;
        this.ratings = ratings;
    }

    public String getIdentifier() { return identifier; }

    public String getName() {
        return name;
    }

    public String[] getAvailablePlatforms() {
        return availablePlatforms;
    }

    public Rating[] getRatings() {
        return ratings;
    }
}
