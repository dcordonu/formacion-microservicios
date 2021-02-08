package com.hiberus.show.api.domain.dto;

public class ReviewDto {

    private final int rating;
    private final String comment;

    public ReviewDto(final int rating, final String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
