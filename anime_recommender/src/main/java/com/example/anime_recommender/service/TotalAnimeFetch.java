package com.example.anime_recommender.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Season;
import com.example.anime_recommender.repository.AnimeRepository;

import jakarta.annotation.PostConstruct;


@Profile("!test")
@Service
public class TotalAnimeFetch {

    private final AnimeRepository animeRepository;
    private final AnimeApiRequests animeApiRequests; 
    private final ScheduleService scheduleService;
    private final PopularCacheService popularCacheService;
    private final RabbitMessage rabbitMessage;

    public TotalAnimeFetch(
        AnimeRepository animeRepository, 
        AnimeApiRequests animeApiRequests, 
        ScheduleService scheduleService,
        PopularCacheService popularCacheService,
        RabbitMessage rabbitMessage
    ) {
        this.animeRepository = animeRepository;
        this.animeApiRequests = animeApiRequests;
        this.scheduleService = scheduleService;
        this.popularCacheService = popularCacheService;
        this.rabbitMessage = rabbitMessage;
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        fetchSeasonalAnime();  // Run immediately once
    }

    //Retrieve current season of anime weekly
    @Scheduled(cron ="0 0 2 ? * SUN") //2:00 am on Sundays
    public ResponseEntity<String> fetchSeasonalAnime(){
        TimeService timeService  = new TimeService();

        ZoneId timezone = ZoneId.of("UTC");
        ZonedDateTime now = ZonedDateTime.now(timezone);

        int curr_year = timeService.currSeasonYear(now);
        Season curr_month = timeService.currSeasonMonth(now);

        String month_field = curr_month.getMalValue();
        
        System.out.println("starting weekly anime fetch...");
        try{

        animeApiRequests.saveSeasonalAnime(curr_year, month_field).get();
        
        Instant beginning = Instant.now().minusSeconds(600);
        Instant end = Instant.now().plusSeconds(600);
        List<Integer> idList = animeRepository.findRecentQuery(beginning, end);
        

        List<CompletableFuture<Anime>> futures = new ArrayList<>();
        for (int id : idList) {
            System.out.println(id);
            rabbitMessage.send(id);
            // futures.add(animeApiRequests.fetchAnimeById(id)
            //     .thenApply(anime -> animeRepository.save(anime))
            // );
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        animeRepository.deleteOldEntries(Instant.now().minus(6, ChronoUnit.DAYS));

        scheduleService.refreshCache();
        popularCacheService.refreshPopularPage();
        popularCacheService.refreshPopularDiscover();

        System.out.println("weekly anime fetch complete");
        return ResponseEntity.ok().build();
    
    }
    catch(Exception e){
        System.out.println("weekly anime fetch failed");
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }
}
