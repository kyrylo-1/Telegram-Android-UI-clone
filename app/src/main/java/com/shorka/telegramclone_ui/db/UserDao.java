package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */

@Dao
public interface UserDao {

//    @Query("SELECT * from user")
//    Flowable<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> getById(long id);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
