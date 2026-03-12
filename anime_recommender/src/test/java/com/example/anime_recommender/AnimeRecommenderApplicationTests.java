package com.example.anime_recommender;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.anime_recommender.service.AnimeApiRequests;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

@SpringBootTest
@ActiveProfiles("test")
class AnimeRecommenderApplicationTests {

	static {
		//Prevent dotenv errors (This may be unnecessary because had another dotenv issue that was solved)
		Dotenv dotenv = null;
		try {
			dotenv = Dotenv.load();
		} catch (DotenvException e) {
			System.out.println("No .env file found, using environment variables");
		}
		if (dotenv != null) {
			System.setProperty("Client_ID", dotenv.get("Client_ID"));
			System.setProperty("allowed_origins", dotenv.get("allowed_origins"));
			
		}
	
			}

	@Autowired
    private AnimeApiRequests animeApiRequests;

	@Test
	void contextLoads() {
		assertNotNull(animeApiRequests);
	}

}
