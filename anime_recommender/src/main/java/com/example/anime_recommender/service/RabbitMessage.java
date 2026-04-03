package com.example.anime_recommender.service;

import org.springframework.stereotype.Service;

import com.example.anime_recommender.model.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class RabbitMessage {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RabbitMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void send(Message message) {
        System.out.println("SENDING TO QUEUE: " + message);
        rabbitTemplate.convertAndSend("anime-fetch-queue", message);
    }
}
