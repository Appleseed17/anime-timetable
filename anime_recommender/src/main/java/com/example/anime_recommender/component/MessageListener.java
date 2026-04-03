package com.example.anime_recommender.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.Message;
import com.example.anime_recommender.service.AnimeApiRequests;
import com.example.anime_recommender.service.PopularCacheService;
import com.example.anime_recommender.service.ScheduleService;
import com.example.anime_recommender.repository.AnimeRepository;


@Component
public class MessageListener {
    private final AnimeRepository animeRepository;
    private final AnimeApiRequests animeApiRequests;
    private final ScheduleService scheduleService;
    private final PopularCacheService popularCacheService;

    public MessageListener(
        AnimeRepository animeRepository,
        AnimeApiRequests animeApiRequests,
        ScheduleService scheduleService,
        PopularCacheService popularCacheService
    ) {
        this.animeRepository = animeRepository;
        this.animeApiRequests = animeApiRequests;
        this.scheduleService = scheduleService;
        this.popularCacheService = popularCacheService;
    }
    

    @RabbitListener(
        queues = "anime-fetch-queue",
        containerFactory = "rabbitListenerContainerFactory"
    )
    public void receive(Message message) throws Exception{
        System.out.println("RECEIVING ID: " + message.getId());
        switch(message.getType()){
            case "ANIME_ID":
                Anime anime = animeApiRequests.fetchAnimeById(message.getId()).get();
                animeRepository.save(anime);
                break;
            case "BATCH_COMPLETE":
                scheduleService.refreshCache();
                popularCacheService.refreshPopularDiscover();
                popularCacheService.refreshPopularPage();
                break;
        }
        
    }
}
