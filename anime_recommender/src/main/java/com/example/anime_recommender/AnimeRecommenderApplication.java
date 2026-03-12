package com.example.anime_recommender;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories //(basePackages = "com.example.anime_recommender.repository")
@EnableScheduling
public class AnimeRecommenderApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		SpringApplication app = new SpringApplication(AnimeRecommenderApplication.class);
		app.setDefaultProperties(Map.of(
			"spring.datasource.url", dotenv.get("db_url"),
			"spring.datasource.username", dotenv.get("db_UserName"),
			"spring.datasource.password", dotenv.get("db_password"),
			"Client_ID", dotenv.get("Client_ID"),
			"allowed_origins", dotenv.get("allowed_origins")
		));
		app.run(args);
	}

}
