package com.example.anime_recommender.model;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NextAiring {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime next_airing;

    public NextAiring (
        LocalDate start_date,
        LocalDate end_date,
        DayOfWeek day_of_the_week,
        LocalTime start_time
    ){

        ZoneId jp = ZoneId.of("Asia/Tokyo");
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        ZonedDateTime jpDateTime = now.withZoneSameInstant(jp);

        ZonedDateTime candidate = jpDateTime
            .with(TemporalAdjusters.nextOrSame(day_of_the_week))
            .with(start_time);
        
        if (start_date != null && candidate.toLocalDate().isBefore(start_date)) {
            candidate = start_date.atTime(start_time).atZone(jp);
            candidate = candidate.with(TemporalAdjusters.nextOrSame(day_of_the_week));
        }

        if (end_date != null && candidate.toLocalDate().isAfter(end_date)) {
            candidate = null;
        }

        this.next_airing = candidate;
    }


}
