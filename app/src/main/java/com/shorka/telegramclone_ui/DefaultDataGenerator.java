package com.shorka.telegramclone_ui;

import android.support.annotation.Nullable;

import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class DefaultDataGenerator {

    public static List<User> generateUsers() {

        final List<User> list = new ArrayList<>();
        list.add(createUserEntity(1, "Kyrylo", "I am android dev", "204-234- 6712",
                "mirecsy23"));
        list.add(createUserEntity(2, "Bob", "", "204-234- 6712", "fdfds2"));
        list.add(createUserEntity(3, "Alex", "hahaha", "234-214- 6715", "f11ds2"));
        list.add(createUserEntity(4, "Ivan", "I live in NYC.", "904-124- 6413", "124_dkf"));
        list.add(createUserEntity(5, "Pavel Durov", null, "204-224- 3242", "hah23"));
        list.add(createUserEntity(6, "Lisa S", null, "204-224- 3242", "niheo"));
        list.add(createUserEntity(7, "Mr. Heisenberg", null, "204-224-0000", "saturn"));
        list.add(createUserEntity(8, "Jack Uni", null, "204-224- 3342", "havka"));
        list.add(createUserEntity(9, "Anna Smith", null, "204-224- 6542", "gal34"));

        return list;
    }

    private static User createUserEntity(long id, String name, @Nullable String bio, String phoneNumber
            , String username) {
        User user = new User(id);
        user.name = name;
        user.bio = bio;
        user.phoneNumber = phoneNumber;
        user.username = username;
        return user;
    }


    public static List<Message> generateMessages() {
        final List<Message> list = new ArrayList<>();
        long recipientId = 2;
        list.add(createMessage(recipientId,"Hi, bruv. Today I did not see you at the gym", "12:34"));
        list.add(createMessage(recipientId,"Are you even lifting bro", "12:35"));

        recipientId = 3;
        list.add(createMessage(recipientId,"So, at 1pm at coffee shop?", "11:00"));
        list.add(createMessage(recipientId,"see you there", "11:10"));

        recipientId = 4;
        list.add(createMessage(recipientId,"I am already at that spot", "16:13"));
        list.add(createMessage(recipientId,"see you there", "16:13"));

        recipientId = 5;
        list.add(createMessage(recipientId,"Do you know where is my keys?", "21:55"));

        recipientId = 6;
        list.add(createMessage(recipientId,"sup", "15:13"));

        recipientId = 7;
        list.add(createMessage(recipientId,"Dont skip my classes anymore", "11:13"));

        recipientId = 8;
        list.add(createMessage(recipientId," need to think about this more carefully", "9:13"));

        recipientId = 9;
        list.add(createMessage(recipientId,"Really?", "16:13"));

        return list;
    }

    private static Message createMessage(long recipientId, String text, String time) {
        Message message = new Message(0);
        message.recipientId = recipientId;
        message.text = text;
        message.date = time;
        message.messageType = Message.RECEIVED;
        return message;
    }

}
