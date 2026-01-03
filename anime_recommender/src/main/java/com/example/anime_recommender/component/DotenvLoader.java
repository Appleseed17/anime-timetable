package com.example.anime_recommender.component;
import io.github.cdimascio.dotenv.Dotenv;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DotenvLoader {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("db_url", dotenv.get("db_url"));
        System.setProperty("db_UserName", dotenv.get("db_UserName"));
        System.setProperty("db_password", dotenv.get("db_password"));
    }
}