package com.example.anime_recommender.model;

import java.util.ArrayList;

//Class to easily cast the MAL anime api JSON into a java object
// structured as:
//     data 
//         node:
//             anime
//         node:
//             anime
//         ...
    
public class AnimeApiResponse {
    private ArrayList<AnimeNode> data;

    public ArrayList<AnimeNode> getData() {
        return data;
    }
   
    public class Paging {
        private String next;
        private String previous;

        public String getNext() {
            return next;
        }
        public String getPrevious() {
            return previous;
        }
    }
}
