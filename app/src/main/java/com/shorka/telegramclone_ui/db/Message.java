package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */

@Entity (tableName = "message")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private long idMessage;

    public long recipientId;
    public String text;
    public String date;

    public Message(long idMessage){
        this.idMessage = idMessage;
    }

    public long getIdMessage() {
        return idMessage;
    }
}
