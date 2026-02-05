package com.example.anime_recommender;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.anime_recommender.service.AnimeApiRequests;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class AnimeRecommenderApplicationTests {

	static {
		Dotenv dotenv = Dotenv.load();
        System.setProperty("Client_ID", dotenv.get("Client_ID"));
        System.setProperty("spring.datasource.url", dotenv.get("db_url"));
        System.setProperty("spring.datasource.username", dotenv.get("db_UserName"));
        System.setProperty("spring.datasource.password", dotenv.get("db_password"));
	}

	@Autowired
    private AnimeApiRequests animeApiRequests;

	@Test
	void contextLoads() {
		assertNotNull(animeApiRequests);
	}

}
