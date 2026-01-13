package com.example.anime_recommender.model;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.qos.logback.core.pattern.parser.Node;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.example.anime_recommender.model.Season;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "anime")
public class Anime {
        @Id
        private int id;
        private String title;
        @Embedded
        private Picture main_picture;
        @Embedded
        private AlternativeTitles alternative_titles;
        private LocalDate start_date;
        private LocalDate end_date;
        @Column(length = 5000)
        private String synopsis;
        private Double mean;
        private Integer rank;
        private Integer popularity;
        private Integer num_list_users;
        private Integer num_scoring_users;
        private String nsfw;
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(
        name = "anime_genres",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
        )
        @OnDelete(action = OnDeleteAction.CASCADE)
        private List<Genre> genres;

        private Instant created_at;
        
        private Instant updated_at;
        private String media_type;
        private String status;
        
        private String source;
        private String rating;
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(
        name = "anime_studios",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id"))
        @OnDelete(action = OnDeleteAction.CASCADE)
        private List<Studio> studios;
        @Embedded
        private Broadcast broadcast;
        @ElementCollection
        @CollectionTable(name = "related_anime", joinColumns = @JoinColumn(name = "anime_id"))
        private List<Container> related_anime;
        @Embedded
        @JsonProperty("start_season")
        private StartSeason startSeason;
        private Instant timeStamp;
        @JsonProperty("num_episodes")
        private int num_episodes;
        @JsonProperty("average_episode_duration")
        private int average_episode_duration;


        public int getEpisodeNum() {
            return num_episodes;
        }
        
        public int getEpisodeDuration() {
            return average_episode_duration;
        }

        public Instant getTimeStamp() {
            return timeStamp;
        }
        
        @JsonProperty("start_date")
        public void setStartDate(String dateStr) {
            if (dateStr == null || dateStr.isEmpty()) {
                this.start_date = null;
                return;
            }

            try {
                if (dateStr.length() == 10) { // "yyyy-MM-dd"
                    this.start_date = LocalDate.parse(dateStr);
                } else if (dateStr.length() == 7) { // "yyyy-MM"
                    this.start_date = LocalDate.parse(dateStr + "-01"); // first day of month
                } else if (dateStr.length() == 4) { // "yyyy"
                    this.start_date = LocalDate.parse(dateStr + "-01-01"); // Jan 1st
                } else {
                    System.out.println("Unexpected date format: " + dateStr);
                    this.start_date = null;
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + dateStr);
                this.start_date = null;
            }
        }

        @JsonProperty("end_date")
        public void setEndDate(String dateStr) {
            if (dateStr == null || dateStr.isEmpty()) {
                this.end_date = null;
                return;
            }

            try {
                if (dateStr.length() == 10) { // "yyyy-MM-dd"
                    this.end_date = LocalDate.parse(dateStr);
                } else if (dateStr.length() == 7) { // "yyyy-MM"
                    this.end_date = LocalDate.parse(dateStr + "-01"); // first day of month
                } else if (dateStr.length() == 4) { // "yyyy"
                    this.end_date = LocalDate.parse(dateStr + "-01-01"); // Jan 1st
                } else {
                    System.out.println("Unexpected date format: " + dateStr);
                    this.end_date = null;
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + dateStr);
                this.start_date = null;
            }
        }

        @PrePersist
        @PreUpdate
        public void touch() {
            this.timeStamp = Instant.now();
        }

        public Broadcast getBroadcast() {
            return broadcast;
        }
        public int getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
        public List<Container> getRelated_anime() {
            return related_anime;
        }
        public Picture getMain_picture() {
            return main_picture;
        }
        public AlternativeTitles getAlternative_titles() {
            return alternative_titles;
        }
        public LocalDate getStart_date() {
            return start_date;
        }
        public LocalDate getEnd_date() {
            return end_date;
        }
        public String getSynopsis() {
            return synopsis;
        }
        public Double getMean() {
            return mean;
        }
        public Integer getRank() {
            return rank;
        }
        public Integer getPopularity() {
            return popularity;
        }
        public Integer getNum_list_users() {
            return num_list_users;
        }
        public Integer getNum_scoring_users() {
            return num_scoring_users;
        }
        public String getNsfw() {
            return nsfw;
        }
        public List<Genre> getGenres() {
            return genres;
        }
        public Instant getCreated_at() {
            return created_at;
        }
        public Instant getUpdated_at() {
            return updated_at;
        }
        public String getMedia_type() {
            return media_type;
        }
        public String getStatus() {
            return status;
        }
        public String getSource() {
            return source;
        }
        public String getRating() {
            return rating;
        }
        public List<Studio> getStudios() {
            return studios;
        }

        @Embeddable
        public static class Picture {
            private String medium;
            private String large;
            public String getMedium() {
                return medium;
            }
            public String getLarge() {
                return large;
            }
            
        }
    
        @Embeddable
        public static class AlternativeTitles {
            @ElementCollection
            @OnDelete(action = OnDeleteAction.CASCADE)
            private List<String> synonyms;
            private String en;
            private String ja;
            
            public String getEn() {
                return en;
            }
            public String getJa() {
                return ja;
            }
            public List<String> getSynonyms() {
                return synonyms;
            }
            
        }
        
        @Embeddable 
        public static class StartSeason {
            private int year;
            
            @Enumerated(EnumType.STRING)
            private Season season;

            public StartSeason() {}

            public int getYear() {
                return year;
            }

            public Season getSeason() {
                return season;
            }
        }
        
        public StartSeason getStartSeason(){
            return startSeason;
        }


        public static class Broadcast {
            private DayOfWeek day_of_the_week;
            @JsonProperty("start_time")
            @JsonFormat(pattern = "HH:mm")
            private LocalTime start_time;

            @JsonProperty("day_of_the_week")
            public void setDay(String dayStr) {
                System.out.println(dayStr);
                if (dayStr == null || dayStr.isEmpty()) {
                    this.day_of_the_week = null;
                    return;
                }

                try {
                    this.day_of_the_week = DayOfWeek.valueOf(dayStr.toUpperCase().strip());
                } catch (IllegalArgumentException e) {
                    this.day_of_the_week = null; // or log a warning
                }
            }

            public DayOfWeek getDay_of_the_week() {
                return day_of_the_week;
            }
            public LocalTime getStart_time() {
                return start_time;
            }
            
        }

        @Embeddable
        public static class Container {
            @Embedded
            private Node node;
            public Node getNode() {
                return node;
            }
            public String getRelation_type() {
                return relation_type;
            }
            public String getRelation_type_formatted() {
                return relation_type_formatted;
            }
            private String relation_type;
            private String relation_type_formatted;
            
        }

        @Embeddable
        public static class Node {
            private long id;
            private String title;
            @Embedded
            private Picture main_picture;
            public long getId() {
                return id;
            }
            public String getTitle() {
                return title;
            }
            public Picture getMain_picture() {
                return main_picture;
            }

        }
        
    }
    

    

    


