package com.example.anime_recommender.controller;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.repository.AnimeRepository;
import com.example.anime_recommender.repository.projection.GenreCount;
import com.example.anime_recommender.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/anime")
public class AnimeRecommender {

    private final AnimeRepository animeRepository;
    private final ScheduleService scheduleService;
   
    // This is the mapping in order to get a JSON request with the query parameters
    // it will use the list within the database of all anime, and create a smaller list with these parameters
    // from this list it will randomly select one
    // query methods will include: genres, rank ranges, studios, score ranges, ep number ranges, episode length ranges, ongoing or concluded
    // Some of these will be added into advanced search possibly, like if you want a random anime at 12 or 13 episodes only
    // this may be too complex so maybe the user selects the range itself that could be cool like on a graph or something awesome 

public AnimeRecommender(AnimeRepository animeRepository, ScheduleService scheduleService) {
    this.animeRepository = animeRepository;
    this.scheduleService = scheduleService;

    }


@GetMapping("/seasonal/popular")
public Page<Anime> getMostPopular(
    Pageable pageable
) {
    return animeRepository.findMostPopular(pageable);

}

@GetMapping(value="/seasonal/weekly", produces = MediaType.APPLICATION_JSON_VALUE)
public String getWeeklySchedule() {
    return scheduleService.getSchedule();
}

@GetMapping("seasonal/genre")
public List<GenreCount> getGenres(){
    return animeRepository.findSeasonalGenreCounts();
}

@GetMapping("seasonal/genre/{genre_name}")
public Page<Anime> getAnimeByGenre(
    @PathVariable String genre_name,
    Pageable pageable
)   {
    return animeRepository.findByGenresName(genre_name, pageable);
}

@GetMapping("/{id}")
public Optional<Anime> getAnime(@PathVariable int id){
    return animeRepository.findById(id);

}



}



