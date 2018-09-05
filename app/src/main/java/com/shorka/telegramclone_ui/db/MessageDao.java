package com.shorka.telegramclone_ui.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/21/2018.
 */

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM message WHERE message.recipientId == :id")
    LiveData<List<Message>> getByRecipientId(long id);


    //TODO: improve query, so it can SELECT *
    @Query("SELECT * FROM message as m " +
            "WHERE (m.recipientId+','+ m.message_date) IN " +
            "(SELECT m2.recipientId +',' +MAX(m2.message_date) FROM message as m2 " +
            "GROUP BY m2.recipientId) " +
            "ORDER BY m.message_date DESC")
    LiveData<List<Message>> getMostRecentDateAndGrouById();

    @Query("DELETE FROM message")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message message);

    @Delete
    void delete(Message ...message);

}
