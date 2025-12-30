package com.example.anime_recommender.model;

import java.util.ArrayList;
import java.util.List;

public class AnimeQuery {
    
    private List<String> genres;
    private Boolean isSeries;
    private int startingRank;
    private int endRank;
    private List<String> studios;
    private int startingScore;
    private int endScore;
    private int maxEpisodes;
    private Boolean isOngoing;

    public AnimeQuery(List<String> genres, Boolean isSeries, int startingRank, int endRank,
            List<String> studios, int startingScore, int endScore, int maxEpisodes, Boolean isOngoing) {
        this.genres = genres;
        this.isSeries = isSeries;
        this.startingRank = startingRank;
        this.endRank = endRank;
        this.studios = studios;
        this.startingScore = startingScore;
        this.endScore = endScore;
        this.maxEpisodes = maxEpisodes;
        this.isOngoing = isOngoing;
    }
    public List<String> getGenres() {
        return genres;
    }
    public Boolean getIsSeries() {
        return isSeries;
    }
    public int getStartingRank() {
        return startingRank;
    }
    public int getEndRank() {
        return endRank;
    }
    public List<String> getStudios() {
        return studios;
    }
    public int getStartingScore() {
        return startingScore;
    }
    public int getEndScore() {
        return endScore;
    }
    public int getMaxEpisodes() {
        return maxEpisodes;
    }
    public Boolean getIsOngoing() {
        return isOngoing;
    }




}
