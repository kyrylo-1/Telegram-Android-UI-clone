package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */

//User Messages DAO object
@Dao
public interface UserMsgDao {


//    @Query("SELECT lastMessage FROM user_messages AS us " +
//            "WHERE us.recipient_id = :recipientId " +
//            "AND us.user_id = :userId")
//    String findLastMessageByUserAndRecipId(long recipientId, long userId);

//
//    @Query("SELECT lastMessage FROM user_messages AS us " +
//            "WHERE us.user_id = :userId")
//    LiveData<List<String>> findLastMessagesByUserId(long userId);

    @Insert
    void insertUserMessages(UserMsgs userMsgs);

    @Update
    void updateUserMessages(UserMsgs userMsgs);

    @Query("SELECT * FROM user_messages WHERE recipient_id = :id")
    LiveData<UserMsgs> getById(long id);

    @Query("SELECT * FROM user_messages")
    LiveData<List<UserMsgs>> getAllUserMessages();

    @Query("DELETE FROM user_messages")
    void deleteAll();

    @Delete
    void delete(UserMsgs userMsgs);
}
