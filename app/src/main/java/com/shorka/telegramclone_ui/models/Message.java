package com.shorka.telegramclone_ui.models;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class Message {

    private  String message;
//    private User sender;
    private int createdAt;

    public Message(String message, int createdAt) {
        this.message = message;
//        this.sender = sender;
        this.createdAt = createdAt;
    }


    public String getMessage() {
        return message;
    }

//    public User getSender() {
//        return sender;
//    }

    public int getCreatedAt() {
        return createdAt;
    }


}
