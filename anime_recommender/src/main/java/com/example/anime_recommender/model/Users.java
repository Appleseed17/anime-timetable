package com.example.anime_recommender.model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY )
    private Long id;
    private String userName;
    private ArrayList<Anime> animeList;
    
    public Users(String userName, ArrayList<Anime> animeList) {
        this.userName = userName;
        this.animeList = animeList;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public ArrayList<Anime> getAnimeList() {
        return animeList;
    }
    public void setAnimeList(ArrayList<Anime> animeList) {
        this.animeList = animeList;
    }

}
