package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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

    @ColumnInfo(name = "message_date")
    public long date;

    public int messageType;


    public Message(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public Date getRealDate(){
        return Converters.fromTimestamp(date);
    }

    public String getStringDate(){
        return  Converters.dateToHourAndMinute(date);
    }


    public void setDate(Date date){
        this.date = Converters.dateToTimestamp(date);
    }
}


