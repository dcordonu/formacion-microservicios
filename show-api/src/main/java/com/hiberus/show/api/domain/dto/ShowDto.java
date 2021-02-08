package com.hiberus.show.api.domain.dto;

public class ShowDto {

    private final String identifier;
    private final String title;
    private final String[] availablePlatforms;
    private final RatingDto[] ratings;

    public ShowDto(final String identifier, final String title, final String[] availablePlatforms, final RatingDto[] ratings) {
        this.identifier = identifier;
        this.title = title;
        this.availablePlatforms = availablePlatforms;
        this.ratings = ratings;
    }

    public String getIdentifier() { return identifier; }

    public String getTitle() {
        return title;
    }

    public String[] getAvailablePlatforms() {
        return availablePlatforms;
    }

    public RatingDto[] getRatings() {
        return ratings;
    }
}
