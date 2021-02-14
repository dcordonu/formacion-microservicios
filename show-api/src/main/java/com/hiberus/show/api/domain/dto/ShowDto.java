package com.hiberus.show.api.domain.dto;

public class ShowDto {

    private final String identifier;
    private final String title;
    private final String[] availablePlatforms;
    private final ReviewDto[] reviews;

    public ShowDto(final String identifier, final String title, final String[] availablePlatforms, final ReviewDto[] reviews) {
        this.identifier = identifier;
        this.title = title;
        this.availablePlatforms = availablePlatforms;
        this.reviews = reviews;
    }

    public String getIdentifier() { return identifier; }

    public String getTitle() {
        return title;
    }

    public String[] getAvailablePlatforms() {
        return availablePlatforms;
    }

    public ReviewDto[] getReviews() {
        return reviews;
    }
}
