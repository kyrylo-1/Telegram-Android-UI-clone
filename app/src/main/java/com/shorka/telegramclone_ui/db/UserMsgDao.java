package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */

//User Messages DAO object
@Dao
public interface UserMsgDao {

    @Insert
    void insertUserMessages(UserMessages userMessages);

    @Update
    void updateUserMessages(UserMessages userMessages);

    @Query("SELECT * FROM user_messages WHERE recipient_id = :id")
    LiveData<UserMessages> getById(long id);

    @Query("SELECT * FROM user_messages")
    LiveData<List<UserMessages>> getAll();

    @Query("DELETE FROM user_messages")
    void deleteAll();

    @Delete
    void delete(UserMessages userMessages);
}
