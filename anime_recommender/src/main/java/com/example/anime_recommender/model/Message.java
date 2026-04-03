package com.example.anime_recommender.model;

public class Message {

    private String type;
    private int id;

    public Message(String type, int id){
        this.type = type;
        this.id = id;
    }
    
    public Message() {}

    public String getType(){
        return type;
    }

    public void setType(String type){ 
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){          
        this.id = id;
    }
}
