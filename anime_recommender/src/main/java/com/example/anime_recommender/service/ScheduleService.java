package com.example.anime_recommender.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.repository.AnimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//Caches the weekly schedule as a JSON string
@Service
public class ScheduleService {
    private String cachedScheduleJson; 

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void refreshCache() {
        int day = LocalDate.now().getDayOfYear();
        int year = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.ofYearDay(year, day);
        LocalDate endDate = startDate.plusDays(7);

        System.out.println(startDate);
        System.out.println(endDate);

        List<Anime> animeList = animeRepository.findWeeklyAnime(startDate, endDate);

         try {
            cachedScheduleJson = objectMapper.writeValueAsString(animeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            cachedScheduleJson = "[]"; 
        }
        System.out.println("Completed Schedule Refresh");

       
    }

    public String getSchedule() {
        if (cachedScheduleJson == null) {
            refreshCache(); 
    }
        return cachedScheduleJson != null ? cachedScheduleJson : "[]";
    }

}
