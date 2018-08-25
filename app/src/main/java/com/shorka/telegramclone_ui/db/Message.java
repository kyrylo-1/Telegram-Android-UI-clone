package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */

@Entity(tableName = "message")
public class Message {

    @Ignore
    public static final int SENT = 0;
    @Ignore
    public static final int RECEIVED = 1;

    @PrimaryKey(autoGenerate = true)
    private long idMessage;

    public long recipientId;
    public String text;

    //TODO: save real date
    public String date;
    public int messageType;


    public Message(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdMessage() {
        return idMessage;
    }
}


