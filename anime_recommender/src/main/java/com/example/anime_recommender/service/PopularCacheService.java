package com.example.anime_recommender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.repository.AnimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PopularCacheService {
    
    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String popularDiscoverJSON;
    private String popularPageJSON;

    public String getPopularDiscoverJSON() {
        return popularDiscoverJSON;
    }

    public String getPopularPageJSON() {
        return popularPageJSON;
    }

    @Transactional
    public void refreshPopularDiscover() {
        Pageable pageable = PageRequest.of(0, 8);
        Page<Anime> animeList = animeRepository.findMostPopular(pageable);
        try {
            popularDiscoverJSON = objectMapper.writeValueAsString(animeList);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            popularDiscoverJSON = "[]"; 
        }
        System.out.println("Completed Popular Discover Refresh");
    }

    @Transactional
    public void refreshPopularPage() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Anime> animeList = animeRepository.findMostPopular(pageable);
        try {
            popularPageJSON = objectMapper.writeValueAsString(animeList.getContent());
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            popularPageJSON = "[]"; 
        }
        System.out.println("Completed Popular Page Refresh");
    }



}
