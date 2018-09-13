package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.IntDef;
import android.util.Log;

import com.shorka.telegramclone_ui.contact_chat_screen.ContactChatFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */

@Entity(tableName = "message")
public class Message {

    //<editor-fold desc="Room save fields">
    @PrimaryKey(autoGenerate = true)
    private long idMessage;
    public long recipientId;
    public String text;


    @ColumnInfo(name = "message_date")
    public long date;

    @MessageType
    public int messageType;
    //</editor-fold>

    @IntDef({MessageType.SENT, MessageType.RECEIVED, MessageType.DRAFT, MessageType.EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageType {
        int SENT = 0;
        int RECEIVED = 1;
        int DRAFT = 2;
        int EMPTY = 3;
    }

    private static final String TAG = "Message";

    public interface SelectionCallBack {
        void onClear();

        void onSelect();
    }

    @Ignore
    private SelectionCallBack listenerSelection;

    public Message(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdMessage() {
        return idMessage;
    }


    //<editor-fold desc="Date properties">
    public Date getRealDate() {
        return Converters.fromTimestamp(date);
    }

    public String getStringDate() {
        return Converters.dateToHourAndMinute(date);
    }


    public void setDate(Date date) {
        this.date = Converters.dateToTimestamp(date);
    }

    public void setDate(long date) {
        this.date = date;
    }
    //</editor-fold>

    public void setSelectionCallback(SelectionCallBack listener) {
        listenerSelection = listener;
    }

    public void clearSelection() {
        Log.d(TAG, "clearSelection: ");
        listenerSelection.onClear();
    }

    public void makeSelection() {
        listenerSelection.onSelect();
    }

}

