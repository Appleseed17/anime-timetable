package com.example.anime_recommender.repository.projection;

//Projection for a return genre object for Genres endpoint
public interface GenreCount {
    String getName();
    long getCount();
}
