package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */

@Entity(tableName = "message")
public class Message {


    private static final String TAG = "Message";

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", recipientId=" + recipientId +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", messageType=" + messageType +
                ", listenerSelection=" + listenerSelection +
                '}';
    }

    public interface SelectionCallBack {
        void onClear();

        void onSelect();
    }

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

    @Ignore
    private SelectionCallBack listenerSelection;

    public Message(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdMessage() {
        return idMessage;
    }


    //<editor-fold desc="Date properties">
    public Date getRealDate(){
        return Converters.fromTimestamp(date);
    }

    public String getStringDate(){
        return  Converters.dateToHourAndMinute(date);
    }


    public void setDate(Date date){
        this.date = Converters.dateToTimestamp(date);
    }
    //</editor-fold>

    public void setSelectionCallback(SelectionCallBack listener){
        listenerSelection = listener;
    }

    public void clearSelection(){
        Log.d(TAG, "clearSelection: " + text);
        listenerSelection.onClear();
    }

    public void makeSelection(){
        Log.d(TAG, "makeSelection: "+text);
        listenerSelection.onSelect();
    }


}

