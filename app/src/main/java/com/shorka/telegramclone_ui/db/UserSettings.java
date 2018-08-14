package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
@Entity
public class UserSettings {


    @PrimaryKey
    @ColumnInfo(name = "user_id")
    private final long mId;

    public int mLanguge;
    public int mTheme;


    public UserSettings(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

}

