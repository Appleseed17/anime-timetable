package com.example.anime_recommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.anime_recommender.model.Anime;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer>{
    

}
