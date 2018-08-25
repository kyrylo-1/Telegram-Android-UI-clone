package com.shorka.telegramclone_ui.db;

import android.app.Application;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public class LocalDatabase {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;

    public LocalDatabase(Application application) {
        userRepo = new UserRepository(application);
        messageRepo = new MessageRepository(application);
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public MessageRepository getMessageRepo() {
        return messageRepo;
    }
}
