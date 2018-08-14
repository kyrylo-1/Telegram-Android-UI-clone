package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */

@Dao
public interface UserMessagesDao {


    @Query("SELECT lastMessage FROM user_messages AS us " +
            "WHERE us.recipient_id = :recipientId " +
            "AND us.user_id = :userId")
    String findLastMessageByUserAndRecipId(long recipientId, long userId);


    @Query("SELECT lastMessage FROM user_messages AS us " +
            "WHERE us.user_id = :userId")
    LiveData<List<String>> findLastMessagesByUserId(long userId);

    @Insert(onConflict = IGNORE)
    void insertUserMessages(UserMessages userMessages);

    @Update(onConflict = REPLACE)
    void updateUserMessages(UserMessages userMessages);

    @Query("DELETE FROM user_messages")
    void deleteAll();

    @Query("SELECT * FROM user_messages")
    LiveData<List<UserMessages>> findAllUserSettings();
}
