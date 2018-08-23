package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> getById(long id);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM contact_Phonebook")
    LiveData<List<PhoneContact>> getPhoneContacts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoneContact(PhoneContact phoneContact);

    @Update
    void updatePhoneContact(PhoneContact phoneContact);
}
