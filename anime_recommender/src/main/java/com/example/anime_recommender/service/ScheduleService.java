package com.example.anime_recommender.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.repository.AnimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;


@Service
public class ScheduleService {
    private String cachedScheduleJson; 

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void refreshCache() {
        LocalDate startDate = LocalDate.of(2026, 1, 11);
        LocalDate endDate = startDate.plusDays(7);

        List<Anime> animeList = animeRepository.findWeeklyAnime(startDate, endDate);

         try {
            cachedScheduleJson = objectMapper.writeValueAsString(animeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            cachedScheduleJson = "[]"; 
        }
        System.out.println("Completed Refresh");

       
    }

    public String getSchedule() {
        if (cachedScheduleJson == null) {
            refreshCache(); 
    }
        return cachedScheduleJson != null ? cachedScheduleJson : "[]";
    }

}
