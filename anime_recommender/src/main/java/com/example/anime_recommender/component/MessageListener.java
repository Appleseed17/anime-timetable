package com.example.anime_recommender.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.service.AnimeApiRequests;
import com.example.anime_recommender.repository.AnimeRepository;


@Component
public class MessageListener {
    private final AnimeRepository animeRepository;
    private final AnimeApiRequests animeApiRequests;

    public MessageListener(
        AnimeRepository animeRepository,
        AnimeApiRequests animeApiRequests
    ) {
        this.animeRepository = animeRepository;
        this.animeApiRequests = animeApiRequests;
    }
    

    @RabbitListener(
        queues = "anime-fetch-queue",
        containerFactory = "rabbitListenerContainerFactory"
    )
    public void receive(Integer id) throws Exception{
        System.out.println("RECEIVING ID: " + id);
        Anime anime = animeApiRequests.fetchAnimeById(id).get();
        animeRepository.save(anime);
    }
}
