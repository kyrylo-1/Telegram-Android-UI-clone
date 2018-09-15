package com.shorka.telegramclone_ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class DefaultDataGenerator {

    private static final String TAG = "DefaultDataGenerator";

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
        user.firstName = name;
        user.bio = bio;
        user.phoneNumber = phoneNumber;
        user.username = username;
        return user;
    }

    @NonNull
    public static List<Message> generateMessages() {
        final List<Message> listMessages = new ArrayList<>();
        long recipientId = 2;

        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.set(2018, Calendar.AUGUST, 21, 12, 33);

        //Imitate todays date
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal2.getTime());
        cal2.add(Calendar.HOUR, -3);
        cal2.add(Calendar.MINUTE, -10);
        listMessages.add(createMessage(recipientId, "Hi, bruv. Today I did not see you at the gym", cal2.getTime()));
        cal2.add(Calendar.MINUTE, 2);

        listMessages.add(createMessage(recipientId, "Are you even lifting bro", cal2.getTime()));

        cal2.add(Calendar.MINUTE, 1);
        listMessages.add(createMessage(recipientId, "I do \n THIS \n EVERY \n DAY", cal2.getTime()));

        cal2.add(Calendar.MINUTE, 1);
        listMessages.add(createMessage(recipientId, "you should too", cal2.getTime()));

        listMessages.add(createMessage(recipientId, "promise me", cal2.getTime()));
        listMessages.add(createMessage(recipientId, "Not tommorow", cal2.getTime()));
        listMessages.add(createMessage(recipientId, "NOW!", cal2.getTime()));
        listMessages.add(createMessage(recipientId, "REMEMBER! \nI  \ndo \n THIS \n EVERY \n DAY", cal2.getTime()));

        cal2.add(Calendar.MINUTE, 2);
        listMessages.add(createMessage(recipientId, "ALRIGHT THEN" +
                "\n MORE TEXT \n AND EVEN more text", cal2.getTime()));

        cal2.add(Calendar.MINUTE, 1);
        listMessages.add(createMessage(recipientId, "Hahahahaha-\bahahaha hahah hahahah hahahah " +
                "hahahahahaha\n hahahaha\nhahah\nhaha", cal2.getTime()));

        //===================================================================================
        recipientId = 3;

        cal1.set(2018, Calendar.AUGUST, 17, 11, 0);
        listMessages.add(createMessage(recipientId, "So, at 1pm at coffee shop?", cal1.getTime()));
        cal1.set(2018, Calendar.AUGUST, 17, 11, 1);
        listMessages.add(createMessage(recipientId, "see you there", cal1.getTime()));
        //===================================================================================
        recipientId = 4;
        cal1.set(2018, Calendar.AUGUST, 20, 16, 13);
        listMessages.add(createMessage(recipientId, "I am already at that spot", cal1.getTime()));
        cal1.set(2018, Calendar.AUGUST, 20, 16, 25);
        listMessages.add(createMessage(recipientId, "where are you? at?", cal1.getTime()));

        //===================================================================================
        recipientId = 5;
        cal1.set(2018, Calendar.MAY, 4, 21, 33);
        listMessages.add(createMessage(recipientId, "Do you know where is my keys?", cal1.getTime()));

        //===================================================================================
        recipientId = 6;
        cal1.set(2018, Calendar.JULY, 30, 17, 41);
        listMessages.add(createMessage(recipientId, "sup", cal1.getTime()));

        //===================================================================================
        recipientId = 7;
        cal1.set(2018, Calendar.JUNE, 25, 12, 49);
        listMessages.add(createMessage(recipientId, "Dont skip my classes anymore", cal1.getTime()));

        //===================================================================================
        recipientId = 8;
        //will show lastMessageLive like it was written 2 days ago
        final Calendar c8 = Calendar.getInstance();
        c8.add(Calendar.DAY_OF_MONTH, -5);
        listMessages.add(createMessage(recipientId, " need to think about this more carefully", c8.getTime()));

        //===================================================================================
        recipientId = 9;
        cal1.set(2018, Calendar.AUGUST, 5, 9, 9);
        listMessages.add(createMessage(recipientId, "Really?", cal1.getTime()));

        return listMessages;
    }

    private static Message createMessage(long recipientId, String text, Date date) {
        Message message = new Message(0);
        message.recipientId = recipientId;
        message.text = text;
        message.setDate(date);
//        lastMessageLive.date = date;
        message.messageType = Message.MessageType.RECEIVED;
        return message;
    }
}

