package com.shorka.telegramclone_ui;

import android.support.annotation.Nullable;
import android.util.Log;

import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


    public static List<Message> generateMessages() {
        final List<Message> list = new ArrayList<>();
        long recipientId = 2;

        Calendar c1 = GregorianCalendar.getInstance();
        c1.set(2018, Calendar.AUGUST, 21,12,33);

//        Log.d(TAG, "generateMessages: " +c1.getTime());
//        Date d = dateFormat.parse(oldDateString);

        list.add(createMessage(recipientId,"Hi, bruv. Today I did not see you at the gym", c1.getTime()));
        c1.set(2018, Calendar.AUGUST, 21,12,35);
        list.add(createMessage(recipientId,"Are you even lifting bro", c1.getTime()));

        recipientId = 3;

        c1.set(2018, Calendar.AUGUST, 17,11,0);
        list.add(createMessage(recipientId,"So, at 1pm at coffee shop?", c1.getTime()));
        c1.set(2018, Calendar.AUGUST, 17,11,1);
        list.add(createMessage(recipientId,"see you there", c1.getTime()));

        recipientId = 4;
        c1.set(2018, Calendar.AUGUST, 20,16,13);
        list.add(createMessage(recipientId,"I am already at that spot", c1.getTime()));
        c1.set(2018, Calendar.AUGUST, 20,16,25);
        list.add(createMessage(recipientId,"where are you? at?", c1.getTime()));

        recipientId = 5;
        c1.set(2018, Calendar.MAY, 4,21,33);
        list.add(createMessage(recipientId,"Do you know where is my keys?", c1.getTime()));

        recipientId = 6;
        c1.set(2018, Calendar.JULY, 30,17,41);
        list.add(createMessage(recipientId,"sup", c1.getTime()));

        recipientId = 7;
        c1.set(2018, Calendar.JUNE, 25,12,49);
        list.add(createMessage(recipientId,"Dont skip my classes anymore", c1.getTime()));

        recipientId = 8;
        c1.set(2018, Calendar.AUGUST, 15,13,0);
        list.add(createMessage(recipientId," need to think about this more carefully", c1.getTime()));

        recipientId = 9;
        c1.set(2018, Calendar.AUGUST, 5,9,9);
        list.add(createMessage(recipientId,"Really?", c1.getTime()));

        return list;
    }

    private static Message createMessage(long recipientId, String text, Date date) {
        Message message = new Message(0);
        message.recipientId = recipientId;
        message.text = text;
        message.realDate = date;
//        message.realDate = date;
        message.messageType = Message.RECEIVED;
        return message;
    }

}
