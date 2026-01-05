package com.example.anime_recommender.service;

//import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.AnimeApiResponse;
import com.example.anime_recommender.repository.AnimeRepository;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import com.example.anime_recommender.model.AnimeNode;
//Service class used for API Requests to MAL Specifically to populate our database every week

@Service
@Transactional
public class AnimeApiRequests {
 
    private WebClient webClient;
    private final AnimeRepository animeRepository;

    public AnimeApiRequests(AnimeRepository animeRepository, WebClient webClient) {
        this.animeRepository = animeRepository;
        this.webClient = webClient;
    }

    //DB function to save all the animes into the db in a list of anime
    public void saveMultiple(ArrayList<Anime> animes) {
        animeRepository.saveAll(animes);
    }  
   
    //Get client ID from .env file (protective)
    @Value("${Client_ID}")
	private String Client_ID;

    //Query the MAL data base for current seasonal anime
    //This will be iterated through multiple times in sizes of 500 just to catch overhead of the season (most seasons shouldnt be over 100)
    @Async
    public CompletableFuture<AnimeApiResponse> seasonalQuery(int year, String season){

             return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/season/{year}/{season}")
                                            .queryParam("limit", 500)
                                            .queryParam("fields", "id,title,main_picture,alternative_titles,start_date,mean,rank,popularity,num_episodes,start_season")
                                            .build(year, season))           //build uri with the respective query parameters
                .header("X-MAL-CLIENT-ID", Client_ID) //set headers
                .retrieve()                                     //retrieeve results
                        //Retrieve API status if there is error
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                        System.err.println("MAL API Error (" + clientResponse.statusCode() + "): " + errorBody);
                        return Mono.error(new RuntimeException("API error: " + errorBody));
                    }))             
                    //covert to AnimeApiResponse                                                                
                .bodyToMono(AnimeApiResponse.class)
                .toFuture(); //will be completed asynchronously
    };


   
    public CompletableFuture<ArrayList<Anime>> fetchSeasonalAnime(int year, String season) {
       
    ArrayList<Anime> animeList = new ArrayList<>();
       return seasonalQuery(year, season).thenApply(animes -> {
             // Explicitly define the 'animes' variable type as AnimeApiResponse
            AnimeApiResponse animeResponse = animes;
            ArrayList<AnimeNode> nodes = animeResponse.getData();
            
            for (AnimeNode node : nodes) {
                animeList.add(node.getNode());

            }
            animeRepository.saveAll(animeList);

            for (Anime anime : animeList) {
                fetchAnimeById(anime.getId()).thenAccept(anim -> {
                    System.out.println(anim.getId());
                    animeRepository.save(anim);
                }
                );
            }
            return animeList;
        }); 
}

    public CompletableFuture<Anime> fetchAnimeById(int id) {


        return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/{id}")
                                            .queryParam("fields", "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics")
                                            .build(id))           //build uri with the respective query parameters
                .header("X-MAL-CLIENT-ID", Client_ID) //set headers
                .retrieve()                                     //retrieeve results
                        //Retrieve API status if there is error
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                        System.err.println("MAL API Error (" + clientResponse.statusCode() + "): " + errorBody);
                        return Mono.error(new RuntimeException("API error: " + errorBody));
                    }))             
                    //covert to AnimeApiResponse                                                                
                .bodyToMono(Anime.class)
                .toFuture()
                .thenApplyAsync(anime->
                    animeRepository.save(anime)
                ); //will be completed asynchronously
    }


}


