package com.example.anime_recommender.controller;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.AnimeApiResponse;
import com.example.anime_recommender.repository.AnimeRepository;
import com.example.anime_recommender.service.AnimeApiRequests;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class AnimeRecommender {

    // This is the mapping in order to get a JSON request with the query parameters
    // it will use the list within the database of all anime, and create a smaller list with these parameters
    // from this list it will randomly select one
    // query methods will include: genres, rank ranges, studios, score ranges, ep number ranges, episode length ranges, ongoing or concluded
    // Some of these will be added into advanced search possibly, like if you want a random anime at 12 or 13 episodes only
    // this may be too complex so maybe the user selects the range itself that could be cool like on a graph or something awesome 
    private final AnimeApiRequests animeService;

public AnimeRecommender(AnimeApiRequests animeService) {
    this.animeService = animeService;
    }


@GetMapping("/anime/seasonal")
public CompletableFuture<ResponseEntity<ArrayList<Anime>>> getMethodName() {

    return animeService.fetchSeasonalAnime(2025, "winter")
        .thenApply(ResponseEntity::ok)
        .exceptionally(ex -> {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().build();
        });
}


}


// //This will be used as the get request to send information back to the user about a user recommended anime list
// @GetMapping("anime/userRecommendation")
// public String getMethodName(@RequestParam String username) {

//     return new String();
// }





// }

