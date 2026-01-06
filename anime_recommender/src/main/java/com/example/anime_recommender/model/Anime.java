package com.example.anime_recommender.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
        private String start_date;
        private String end_date;
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
        private List<Genre> genres;

        private String created_at;
        private String updated_at;
        private String media_type;
        private String status;
        @Embedded
        private MyListStatus my_list_status;
        private String source;
        private String rating;
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(
        name = "anime_studios",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id"))
        private List<Studio> studios;
        @Embedded
        private Broadcast broadcast;
        @ElementCollection
        @CollectionTable(name = "related_anime", joinColumns = @JoinColumn(name = "anime_id"))
        private List<Container> related_anime;
        @Embedded
        private StartSeason start_season;

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
        public String getStart_date() {
            return start_date;
        }
        public String getEnd_date() {
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
        public String getCreated_at() {
            return created_at;
        }
        public String getUpdated_at() {
            return updated_at;
        }
        public String getMedia_type() {
            return media_type;
        }
        public String getStatus() {
            return status;
        }
        public MyListStatus getMy_list_status() {
            return my_list_status;
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
        private static class Picture {
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
        private static class AlternativeTitles {
            @ElementCollection
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
        private static class MyListStatus {
            @Column(name = "list_status")
            private String status;
            private Integer score;
            private Integer num_episodes_watched;
    
            public String getStatus() {
                return status;
            }
            public Integer getScore() {
                return score;
            }
            public Integer getNum_episodes_watched() {
                return num_episodes_watched;
            }
            
        } 

        

        

        
        private static class StartSeason {
            private int year;
            private Season season;

            public int getYear() {
                return year;
            }

            public Season getSeason() {
                return season;
            }
        }
        
        public StartSeason getStartSeason(){
            return start_season;
        }


        private static class Broadcast {
            private String day_of_the_week;
            private String start_time;

            public String getDay_of_the_week() {
                return day_of_the_week;
            }
            public String getStart_time() {
                return start_time;
            }
            
        }

        @Embeddable
        private static class Container {
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
        private static class Node {
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
    

    

    


