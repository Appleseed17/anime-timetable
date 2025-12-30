package com.example.anime_recommender.model;

import java.util.ArrayList;

public class AnimeApiResponse {
    private ArrayList<AnimeNode> data;
    //private ArrayList<Paging> paging;
    

    public ArrayList<AnimeNode> getData() {
        return data;
    }
    // public ArrayList<Paging> getPaging() {
    //     return paging;
    // }


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
