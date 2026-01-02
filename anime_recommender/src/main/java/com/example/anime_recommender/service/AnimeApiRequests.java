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

    //Query the MAL data base for all anime
    //This will be iterated through multiple times in sizes of 200 due to the api overload of requests
    @Async
    public CompletableFuture<AnimeApiResponse> seasonal_query(int year, String season){

             return webClient.get()
                                            .uri(uriBuilder -> uriBuilder.path("/ranking")
                                                                        .queryParam("ranking_type", "all")
                                                                        .queryParam("limit", 200)
                                                                        .queryParam("offset", currentRank)
                                                                        .build())           //build uri with the respective query parameters
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


    @Async
    public CompletableFuture<Anime> FetchAnimeDetails(int id){
        String fields = "fields=id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics";
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/{id}")
                                                .queryParam("fields",fields)
                                                .build(id))
                .header("X-MAL-CLIENT-ID", Client_ID)
                .retrieve()
                .bodyToMono(Anime.class)
                .toFuture();

        
    }

    public CompletableFuture<ArrayList<Anime>> fetchTotalAnimes(int year, String season) {
       
    ArrayList<Anime> animeList = new ArrayList<>();
       return seasonal_query(year, season).thenCompose(animes -> {
             // Explicitly define the 'animes' variable type as AnimeApiResponse
            AnimeApiResponse animeResponse = animes;
            return CompletableFuture.supplyAsync(() -> {
                for (var data : animeResponse.getData()) {
                    try {
                        int id = data.getNode().getId();
                        Anime animeDetails = FetchAnimeDetails(id).get(); // block until response
                        animeList.add(animeDetails);
                        System.out.println(animeDetails.getTitle());

                        //Thread.sleep(500); // 500ms delay
                    } catch (Exception e) {
                        System.err.println("Error fetching anime ID " + data.getNode().getId());
                    }
                }

                animeRepository.saveAll(animeList);
                return animeList;
            });
        }); 
}


}

