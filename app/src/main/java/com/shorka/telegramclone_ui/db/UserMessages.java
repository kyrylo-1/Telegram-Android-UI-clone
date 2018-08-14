package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.shorka.telegramclone_ui.models.Message;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */

//@Entity(tableName = "user_messages",
//        foreignKeys = @ForeignKey(entity = User.class,
//        parentColumns = "id", childColumns = "user_id", onDelete = CASCADE))

@Entity(tableName = "user_messages")
public class UserMessages {

    @PrimaryKey
    @ColumnInfo(name = "recipient_id")
    private final long mRecipientId;

    @ColumnInfo(name = "user_id")
    private final long mUserId;

    public String lastMessage;

    public long getRecipientId() {
        return mRecipientId;
    }


    public long getUserId() {
        return mUserId;
    }

    public UserMessages(long recipientId, long userId) {
        mRecipientId = recipientId;
        mUserId = userId;
    }

}
