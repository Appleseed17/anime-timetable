package com.example.anime_recommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Season;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer>{
    
    @Query("select a.id from Anime a where a.start_season.season = :season and a.start_season.year = :year")
    List<Integer> findBySeason(
        @Param("year") int year,
        @Param("season") Season season
    );

}
