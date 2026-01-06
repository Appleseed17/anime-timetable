package com.example.anime_recommender.service;

import com.example.anime_recommender.model.Season;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

    ZoneId timezone = ZoneId.of("UTC");

    public int currSeasonYear() {
        return ZonedDateTime.now(timezone).getYear();
    }

    public int prevSeasonYear() {
        return ZonedDateTime.now(timezone).minusMonths(3).getYear();
    }

    public Season currSeasonMonth() {
        switch (ZonedDateTime.now(timezone).getMonthValue()) {
            case 1, 2, 3:
                return Season.WINTER; 
            case 4, 5, 6:
                return Season.SPRING;
            case 7, 8, 9:
                return Season.SUMMER;
            default:
                return Season.WINTER;
        }
    }

    public Season prevSeasonMonth() {
        switch (ZonedDateTime.now(timezone).minusMonths(3).getMonthValue()) {
            case 1, 2, 3:
                return Season.WINTER; 
            case 4, 5, 6:
                return Season.SPRING;
            case 7, 8, 9:
                return Season.SUMMER;
            default:
                return Season.WINTER;
        }
    }




}