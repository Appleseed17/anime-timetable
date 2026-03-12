package com.example.anime_recommender.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.anime_recommender.model.Anime;
import com.example.anime_recommender.repository.AnimeRepository;
import com.example.anime_recommender.repository.projection.GenreCount;
import com.example.anime_recommender.service.PopularCacheService;
import com.example.anime_recommender.service.ScheduleService;

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
    private final PopularCacheService popularCacheService;
   

    public AnimeRecommender(AnimeRepository animeRepository, ScheduleService scheduleService, PopularCacheService popularCacheService) {
        this.animeRepository = animeRepository;
        this.scheduleService = scheduleService;
        this.popularCacheService = popularCacheService;
        }

    // Discover page with the initial cached popular first page (recached every week)
    @GetMapping(value = "/seasonal/discover", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDiscoverAll() {
        return popularCacheService.getPopularDiscoverJSON();
    }

    // Cached top 3 most popular anime (recached every week)
    @GetMapping(value = "/seasonal/popular/preview", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMostPopular() {
        return popularCacheService.getPopularPageJSON();
    }

    // PARAMS: Pageable
    // returns page of most popular anime oganized by desc rank
    @GetMapping("/seasonal/popular")
    public Page<Anime> getAllPopular(
        Pageable pageable
    ) {
        return animeRepository.findMostPopular(pageable);
    }

    // Weekly anime schedule (Cached every week)
    @GetMapping(value = "/seasonal/weekly", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeeklySchedule() {
        return scheduleService.getSchedule();
    }

    // Gets all genres and their counts
    @GetMapping("seasonal/genre")
    public List<GenreCount> getGenres(){
        return animeRepository.findSeasonalGenreCounts();
    }

    // PARAMS: Pageable
    // gets anime for this genre
    @GetMapping("seasonal/genre/{genre_name}")
    public Page<Anime> getAnimeByGenre(
        @PathVariable String genre_name,
        Pageable pageable
    )   {
        return animeRepository.findByGenresName(genre_name, pageable);
    }

    // PARAMS: pageable
    // Returns anime object if exists
    @GetMapping("/{id}")
    public Optional<Anime> getAnime(@PathVariable int id){
        return animeRepository.findById(id);

    }
}
