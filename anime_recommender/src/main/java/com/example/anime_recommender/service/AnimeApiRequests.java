package com.example.anime_recommender.service;

import java.time.Duration;
//import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.AnimeApiResponse;
import com.example.anime_recommender.repository.AnimeRepository;
import jakarta.transaction.Transactional;
import reactor.util.retry.Retry;

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
   
    @Value("${Client_ID}")
	private String Client_ID;

    //Query the MAL data base for current seasonal anime
    //This will be iterated through multiple times in sizes of 500 just to catch overhead of the season (most seasons shouldnt be over 100)
    @Async
    public CompletableFuture<AnimeApiResponse> fetchSeasonalAnime(int year, String season){

             return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/season/{year}/{season}")
                                            .queryParam("limit", 500)
                                            .queryParam("nsfw", true)
                                            .queryParam("fields", "id,title,main_picture,alternative_titles,start_date,mean,rank,popularity,num_episodes,start_season,nsfw")
                                            .build(year, season))           //build uri with the respective query parameters
                .header("X-MAL-CLIENT-ID", Client_ID) //set headers
                .retrieve()  //->body to AnimeApiResponse                                   
                .onStatus(  //retrieve API status if there is error
                    HttpStatusCode::isError,
                    clientResponse -> clientResponse.createException()
    )             
                 //covert to AnimeApiResponse                                                                
                .bodyToMono(AnimeApiResponse.class)
                .retryWhen( //retry logic
                    Retry.backoff(3, Duration.ofSeconds(500))
                    .filter(ex -> 
                        ex instanceof WebClientResponseException wce &&
                        wce.getStatusCode().is5xxServerError())
                )
                .toFuture(); //will be completed asynchronously
    };


//Used for casting Anime API Requests from AnimeNode objects to Anime Objects
    public CompletableFuture<ArrayList<Anime>> saveSeasonalAnime(int year, String season) {
       
    ArrayList<Anime> animeList = new ArrayList<>();
       return fetchSeasonalAnime(year, season).thenApply(animes -> {
             //Explicitly define the 'animes' variable type as AnimeApiResponse
            AnimeApiResponse animeResponse = animes;
            ArrayList<AnimeNode> nodes = animeResponse.getData();
            
            for (AnimeNode node : nodes) {
                
                animeList.add(node.getNode());

            }
            animeRepository.saveAll(animeList);
            return animeList;
        }); 
}


//Used to Request for all anime details for each anime by id.
//Prevents large batch sizes of requests and prevents large batch errors
    public CompletableFuture<Anime> fetchAnimeById(int id) {
        return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/{id}")
                                            .queryParam("nsfw", true)
                                            .queryParam("fields", "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics")
                                            .build(id))           
                .header("X-MAL-CLIENT-ID", Client_ID) 
                .retrieve()                                     
                .onStatus( 
                    HttpStatusCode::isError,
                    clientResponse -> clientResponse.createException()
                )             
                //covert to Anime                                                              
                .bodyToMono(Anime.class)
                .retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(500))
                    .filter(ex -> 
                        ex instanceof WebClientResponseException wce &&
                        wce.getStatusCode().is5xxServerError())
                )
                .toFuture();
    }

}


