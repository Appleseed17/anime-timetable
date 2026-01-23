package com.example.anime_recommender.controller;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.model.AnimeApiResponse;
import com.example.anime_recommender.model.Season;
import com.example.anime_recommender.repository.AnimeRepository;
import com.example.anime_recommender.service.AnimeApiRequests;
import com.example.anime_recommender.service.TimeService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@RequestMapping("/api/anime")
public class AnimeRecommender {

    private final AnimeRepository animeRepository;
    private final TimeService timeService;
    private final AnimeApiRequests animeApiRequests;
    // This is the mapping in order to get a JSON request with the query parameters
    // it will use the list within the database of all anime, and create a smaller list with these parameters
    // from this list it will randomly select one
    // query methods will include: genres, rank ranges, studios, score ranges, ep number ranges, episode length ranges, ongoing or concluded
    // Some of these will be added into advanced search possibly, like if you want a random anime at 12 or 13 episodes only
    // this may be too complex so maybe the user selects the range itself that could be cool like on a graph or something awesome 

    

public AnimeRecommender(AnimeRepository animeRepository, TimeService timeService, AnimeApiRequests animeApiRequests) {
    this.animeRepository = animeRepository;
    this.timeService = timeService;
    this.animeApiRequests = animeApiRequests;
    }


@GetMapping("/seasonal/popular")
public List<Anime> getMostPopular() {
    Pageable pageable = PageRequest.of(0, 3);
    return animeRepository.findMostPopular(pageable);

}


@GetMapping("/seasonal/weekly")
public List<Anime> getWeeklySchedule() {

    //will be replaced with cron function every week
    LocalDate startDate = LocalDate.of(2026, 1, 11);
    LocalDate endDate = startDate.plusDays(7);

    return animeRepository.findWeeklyAnime(startDate, endDate);
}


@GetMapping("/{id}")
public Optional<Anime> getAnime(@PathVariable int id){
    return animeRepository.findById(id);

}

@GetMapping("seasonal/genre/{genre_name}")
public List<Anime> getAnimeByGenre(@PathVariable String genre_name){
    return animeRepository.findByGenres_Name(genre_name);
}


}



