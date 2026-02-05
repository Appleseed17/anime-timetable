package com.example.anime_recommender.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.anime_recommender.service.TimeService.*;
import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Season;
import com.example.anime_recommender.repository.AnimeRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service

public class TotalAnimeFetch {

    private final AnimeRepository animeRepository;
    private final AnimeApiRequests animeApiRequests; 

    
    public TotalAnimeFetch(AnimeRepository animeRepository, AnimeApiRequests animeApiRequests) {
        this.animeRepository = animeRepository;
        this.animeApiRequests = animeApiRequests;
    }

    private ArrayList<Anime> list;
    
    
    @PostConstruct
    public void runOnStartup() {
        // animeRepository.deleteSeason(2025, Season.WINTER);
        fetchSeasonalAnime();  // Run immediately once

    }


    @Scheduled(cron ="0 0 2 ? * SUN") 
    public ResponseEntity<String> fetchSeasonalAnime(){
        TimeService timeService  = new TimeService();

        ZoneId timezone = ZoneId.of("UTC");
        ZonedDateTime now = ZonedDateTime.now(timezone);

        int curr_year = timeService.currSeasonYear(now);
        Season curr_month = timeService.currSeasonMonth(now);

        String month_field = curr_month.getMalValue();


        System.out.println("starting weekly anime fetch...");
        try{
        
        list = animeApiRequests.saveSeasonalAnime(curr_year, month_field).get();

        Instant beginning = Instant.now().minusSeconds(600);
        Instant end = Instant.now().plusSeconds(600);
        List<Integer> idList = animeRepository.findRecentQuery(beginning, end);
        
        System.out.println(idList);

        for (int id : idList) {
            System.out.println(id);
            animeApiRequests.saveAnimeById(id);
        }

        System.out.println("weekly anime fetch complete");
        System.out.println(list.size());
        return ResponseEntity.ok().build();
    
    }
    catch(Exception e){
        System.out.println("weekly anime fetch failed");
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }
}
