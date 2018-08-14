package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    private final long id;

    public String name;

    public String phoneNumber;

    @Nullable
    public String bio;

    @Nullable
    public String username;

    public User(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }


}
