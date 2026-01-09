package com.example.anime_recommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Season;

import jakarta.transaction.Transactional;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer>{
    
    @Query("select a.id from Anime a where a.startSeason.season = :season and a.startSeason.year = :year")
    List<Integer> findBySeason(
        @Param("year") int year,
        @Param("season") Season season
    );

    @Modifying
    @Transactional
    @Query("DELETE from Anime a where a.startSeason.season = :season and a.startSeason.year = :year")
    int deleteSeason ( 
        @Param("year") int year,
        @Param("season") Season season
    );

    @Query("SELECT a from Anime a where a.broadcast.day_of_the_week IS NOT NULL AND a.broadcast.start_time IS NOT NULL AND a.startSeason.season = :season")
    List<Anime> findScheduledAnime(
        @Param("season") Season season
    );

}
