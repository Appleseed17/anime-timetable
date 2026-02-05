package com.example.anime_recommender.service;

import com.example.anime_recommender.model.Season;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

    public int currSeasonYear(ZonedDateTime now) {
        return now.getYear();
    }

    public int prevSeasonYear(ZonedDateTime now) {
        return now.minusMonths(3).getYear();
    }

    public Season currSeasonMonth(ZonedDateTime now) {
        switch (now.getMonthValue()) {
            case 1, 2, 3:
                return Season.WINTER; 
            case 4, 5, 6:
                return Season.SPRING;
            case 7, 8, 9:
                return Season.SUMMER;
            default:
                return Season.FALL;
        }
    }

    public Season prevSeasonMonth(ZonedDateTime now) {
        switch (now.minusMonths(3).getMonthValue()) {
            case 1, 2, 3:
                return Season.WINTER; 
            case 4, 5, 6:
                return Season.SPRING;
            case 7, 8, 9:
                return Season.SUMMER;
            default:
                return Season.FALL;
        }
    }

}
