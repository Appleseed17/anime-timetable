package com.example.anime_recommender.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Season;
import com.example.anime_recommender.repository.projection.GenreCount;

import jakarta.transaction.Transactional;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer>{
    

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Anime a
        WHERE a.timeStamp < :oldTimeStamp
    """)
    int deleteOldEntries ( 
        @Param("OldStamp") Instant oldTimeStamp
    );

    @Query("""
        SELECT a from Anime a 
        WHERE a.start_date <= :endOfWeek 
        AND (
            end_date IS NULL 
            OR end_date >= :startOfWeek
            ) 
        AND a.broadcast.day_of_the_week IS NOT NULL 
        AND a.broadcast.start_time IS NOT NULL 
        AND (
        a.status = 'currently_airing' 
        OR a.status = 'not_yet_aired'
            )
        
        ORDER BY a.broadcast.day_of_the_week ASC, a.broadcast.start_time ASC
    """)
    List<Anime> findWeeklyAnime(
        @Param("startOfWeek") LocalDate startOfWeek,
        @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("""
        SELECT a FROM Anime a
        WHERE a.num_list_users IS NOT NULL
        AND a.broadcast.day_of_the_week IS NOT NULL
        AND a.broadcast.start_time IS NOT NULL
        AND (
            a.status = 'currently_airing'
            OR a.status = 'not_yet_aired'
            )
        ORDER BY a.rank ASC, a.popularity ASC, a.num_list_users ASC
    """)
    Page<Anime> findMostPopular(
        Pageable pageable
    );

    @Query("""
        SELECT a.id
        FROM Anime a
        WHERE a.timeStamp >= :beginning
        AND a.timeStamp <= :end    
        """)
    List<Integer> findRecentQuery(
        @Param("beginning") Instant beginning,
        @Param("end") Instant end
    );

    @Query("""
        SELECT DISTINCT a
        FROM Anime a
        JOIN a.genres g
        WHERE g.name = :genreName
        AND a.broadcast.day_of_the_week IS NOT NULL
        AND a.broadcast.start_time IS NOT NULL
        AND (
            a.status = 'currently_airing'
            OR a.status = 'not_yet_aired'
            )
        ORDER BY 
            rank NULLS LAST,
            rank ASC, 
            num_list_users DESC
    """)
    Page<Anime> findByGenresName(String genreName, Pageable pageable);

    @Query("""
        SELECT DISTINCT COUNT(a) as count, g.name as name
        FROM Anime a
        JOIN a.genres g
        WHERE a.broadcast.day_of_the_week IS NOT NULL
        AND a.broadcast.start_time IS NOT NULL
        AND (
            a.status = 'currently_airing'
            OR a.status = 'not_yet_aired'
            )
        GROUP BY g.name
        ORDER BY count DESC
            """)
    List<GenreCount> findSeasonalGenreCounts();

}
