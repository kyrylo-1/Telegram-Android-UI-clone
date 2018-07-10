//package com.shorka.telegramclone_ui;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;
//
//import com.shorka.telegramclone_ui.entities.UserEntity;
//
//import java.util.List;
//
///**
// * Created by Kyrylo Avramenko on 6/27/2018.
// */
//
//@Dao
//public interface UserDao {
//
//    @Query("SELECT * FROM userentity")
//    List<UserEntity> getAll();
//
//    @Query("SELECT * FROM userentity WHERE id = :id")
//    UserEntity getById(long id);
//
//    @Insert
//    void insert(UserEntity user);
//
//    @Update
//    void update(UserEntity user);
//
//    @Delete
//    void delete(UserEntity user);
//}
