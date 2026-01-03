// package com.example.anime_recommender.service;

// import java.util.ArrayList;
// import java.util.concurrent.CompletableFuture;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;

// import com.example.anime_recommender.model.Anime;

// import jakarta.annotation.PostConstruct;

// @Service
// public class TotalAnimeFetch {

//     @Autowired
//     private AnimeApiRequests animeApiRequests;
//     private ArrayList<Anime> list;
//     private int i = 26800;
    
//     @PostConstruct
//     public void runOnStartup() {
//         fetchWeeklyAnimeList();  // Run immediately once

//     }


//     @Scheduled(cron ="0 0 2 ? * SUN") 
//     public ResponseEntity<String> fetchWeeklyAnimeList(){

//         System.out.println("starting weekly anime fetch...");
//         try{
//         do {
//             list = animeApiRequests.fetchTotalAnimes(i).get();
//             Thread.sleep(300000);
//             i+=200;
//         } while (i<30000);
//         System.out.println("weekly anime fetch complete");
//         System.out.println(list.size());
//         return ResponseEntity.ok().build();
    
//     }
//     catch(Exception e){
//         System.out.println("weekly anime fetch failed");
//         System.out.println(e.getMessage());
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//     }
//     }
// }
