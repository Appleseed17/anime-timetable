package com.example.anime_recommender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
 @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://api.myanimelist.net/v2/anime").build();
    }
}
