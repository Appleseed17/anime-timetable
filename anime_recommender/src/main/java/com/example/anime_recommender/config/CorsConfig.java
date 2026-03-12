package com.example.anime_recommender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //Only allowing front end to access endpoints
    @Value("${allowed_origins}")
	private String allowed_origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(allowed_origins)
            .allowedMethods("GET")
            .allowedHeaders("*");
    }

}
