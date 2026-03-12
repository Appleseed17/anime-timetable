package com.example.anime_recommender.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.example.anime_recommender.model.Season;


// Tests the TimeService Functions
public class TimeServiceTest {
    
    TimeService timeService = new TimeService();
    
    ZoneId timezone = ZoneId.of("UTC");
    ZonedDateTime feb28 = ZonedDateTime.of
        (2025, 2, 28,
         0, 0, 0, 
         0, timezone);


    @Test
    void currSeasonYearTest() {
        int currSeasonYear = timeService.currSeasonYear(feb28);
        assertEquals(2025, currSeasonYear);
    }

    // Subtracts 3 months from given date
    @Test 
    void prevSeasonYearTest() {
        int prevSeasonYear = timeService.prevSeasonYear(feb28);
        assertEquals(2024, prevSeasonYear);
    }

    @Test
    void currSeasonMonthTest() {
        assertEquals(Season.WINTER, timeService.currSeasonMonth(feb28));
        assertEquals(Season.SPRING, timeService.currSeasonMonth(feb28.plusMonths((3))));
    }

    // Subtracts 3 months from given date
    @Test
    void prevSeasonMonthTest() {
        assertEquals(Season.FALL, timeService.prevSeasonMonth(feb28));

    }

}
