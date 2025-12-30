package com.example.anime_recommender.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Genre {

        @Id
        private int id;

        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        
}
