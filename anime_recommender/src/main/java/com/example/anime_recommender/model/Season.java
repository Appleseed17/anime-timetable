package com.example.anime_recommender.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Season {
    WINTER("winter"),
    SPRING("spring"),
    SUMMER("summer"),
    FALL("fall");

    private final String malValue;

    Season(String malValue) {
        this.malValue = malValue;
    }

    public String getMalValue() {
        return malValue;
    }

    @JsonCreator
    public static Season fromString(String value) {
        return Season.valueOf(value.toUpperCase());
    }
}