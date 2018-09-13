package com.shorka.telegramclone_ui.db;

import java.util.Calendar;

/**
 * Created by Kyrylo Avramenko on 9/13/2018.
 */
public class MessageHelper {

    public static Message createInstance(long recipientId, String text) {
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.setDate(Calendar.getInstance().getTime());
        return m;
    }

    public static Message createInstance(long recipientId, String text, long date) {
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.setDate(date);
        return m;
    }

    public static Message createEmptyMessageInstance(long recipientId){
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = "";
        m.setDate(0);
        m.messageType = Message.MessageType.EMPTY;
        return m;
    }
}
