package com.hiberus.show.api.domain.dto;

public class RatingDto {

    private int punctuation;
    private String comment;

    public RatingDto(final int punctuation, final String comment) {
        this.punctuation = punctuation;
        this.comment = comment;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public String getComment() {
        return comment;
    }
}
