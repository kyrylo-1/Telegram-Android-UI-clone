package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kyrylo Avramenko on 8/23/2018.
 */

@Entity(tableName = "contact_Phonebook")
public class PhoneContact {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String phoneNumber;
    private String name;

    public PhoneContact(long id, String phoneNumber, String name) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getId() {
        return id;
    }
}
