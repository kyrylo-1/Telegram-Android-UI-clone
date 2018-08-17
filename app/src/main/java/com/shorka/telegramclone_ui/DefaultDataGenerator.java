package com.shorka.telegramclone_ui;

import android.support.annotation.Nullable;

import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserMsgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class DefaultDataGenerator {

    public static List<User> generateUser() {

        final List<User> list = new ArrayList<>();
        list.add(createUserEntity(1, "Kyrylo", "I am android dev", "204-234- 6712",
                "mirecsy23"));
        list.add(createUserEntity(2, "Bob", "", "204-234- 6712", "fdfds2"));
        list.add(createUserEntity(4, "Alex", "hahaha", "234-214- 6715", "f11ds2"));
        list.add(createUserEntity(5, "Ivan", "I live in NYC.", "904-124- 6413", "124_dkf"));
        list.add(createUserEntity(6, "Pavel Durov", null, "204-224- 3242", "hah23"));
        list.add(createUserEntity(7, "Lisa S", null, "204-224- 3242", "niheo"));
        list.add(createUserEntity(8, "Mr. Heisenberg", null, "204-224-0000", "saturn"));
        list.add(createUserEntity(9, "Jack Uni", null, "204-224- 3342", "havka"));
        list.add(createUserEntity(10, "Anna Smith", null, "204-224- 6542", "gal34"));

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


    public static List<UserMsgs> generateUserMessages() {
        final List<UserMsgs> list = new ArrayList<>();
        long userId = 1;
        list.add(createUserMessages(2, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(4, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(5, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(6, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(7, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(8, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(9, userId, "Are you even lifting, bro?"));
        list.add(createUserMessages(10, userId, "Are you even lifting, bro?"));

        return list;
    }

    private static UserMsgs createUserMessages(long recipientId, long userId, String lastMessage) {
        UserMsgs userMessages = new UserMsgs(recipientId, userId);
        userMessages.lastMessage = lastMessage;
        return userMessages;
    }

}
