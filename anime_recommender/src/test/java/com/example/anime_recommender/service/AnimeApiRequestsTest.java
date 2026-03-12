package com.example.anime_recommender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.AnimeApiResponse;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AnimeApiRequestsTest {

    @Autowired
    private AnimeApiRequests animeApiRequests;

    // Tests api retrieval + casting to different objects
    @Test
    void fetchSeasonalAnimeTest() {
        AnimeApiResponse response = animeApiRequests.fetchSeasonalAnime(2025, "winter").join();
        int size = response.getData().size();
        assertTrue(size > 0);    
    }

    //Tests getting the exact anime by id
    @Test
    void fetchAnimeByIdTest() {
        Anime anime = animeApiRequests.fetchAnimeById(21).join();
        assertEquals(anime.getAlternative_titles().getEn(), "One Piece");     
    }
    
}
