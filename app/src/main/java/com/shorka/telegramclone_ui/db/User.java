package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    private final long id;

    public String firstName;

    public String lastName;

    public String phoneNumber;

    @Nullable
    public String bio;

    @Nullable
    public String username;

    public String picUrl;


    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFullName(){

        if(TextUtils.isEmpty(lastName))
            return firstName;

        return firstName + " " + lastName;
    }


}
