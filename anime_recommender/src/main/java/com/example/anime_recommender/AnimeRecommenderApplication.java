package com.example.anime_recommender;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories //(basePackages = "com.example.anime_recommender.repository")
@EnableScheduling
public class AnimeRecommenderApplication {

	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(AnimeRecommenderApplication.class);
		app.setDefaultProperties(Map.of(
			"spring.datasource.url", System.getenv("db_url"),
			"spring.datasource.username", System.getenv("db_UserName"),
			"spring.datasource.password", System.getenv("db_password"),
			"Client_ID", System.getenv("Client_ID"),
			"allowed_origins", System.getenv("allowed_origins")
		));
		app.run(args);
	}

}
