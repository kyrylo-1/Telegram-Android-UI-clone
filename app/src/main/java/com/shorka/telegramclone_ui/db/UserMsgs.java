package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */

//@Entity(tableName = "user_messages",
//        foreignKeys = @ForeignKey(entity = User.class,
//        parentColumns = "id", childColumns = "user_id", onDelete = CASCADE))
@Entity(tableName = "user_messages")
public class UserMsgs {

    @PrimaryKey
    @ColumnInfo(name = "recipient_id")
    private long recipientId;

    @ColumnInfo(name = "user_id")
    private long userId;

    public String lastMessage;

    public long getRecipientId() {
        return recipientId;
    }


    public long getUserId() {
        return userId;
    }

    public UserMsgs(long recipientId, long userId) {
        this.recipientId = recipientId;
        this.userId = userId;
    }


}
